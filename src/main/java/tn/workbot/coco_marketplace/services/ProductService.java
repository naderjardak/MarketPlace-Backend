package tn.workbot.coco_marketplace.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.workbot.coco_marketplace.Api.OrderMailSenderService;
import tn.workbot.coco_marketplace.Api.OrderStatsPDFGenerator;
import tn.workbot.coco_marketplace.configuration.SessionService;
import tn.workbot.coco_marketplace.entities.*;
import tn.workbot.coco_marketplace.entities.enmus.ProductStatus;
import tn.workbot.coco_marketplace.entities.enmus.RoleType;
import tn.workbot.coco_marketplace.entities.enmus.SupplierRequestStatus;
import tn.workbot.coco_marketplace.repositories.*;
import tn.workbot.coco_marketplace.services.interfaces.ProductInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

@Service
@Slf4j
public class ProductService implements ProductInterface {

    private static final Logger logger = LoggerFactory.getLogger(OrderStatsPDFGenerator.class);
    @Autowired
    ProductRepository productRepository;
    @Autowired
    StoreService storeService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    PromotionCodeRepository promotionCodeRepository;
    @Autowired
    OrderMailSenderService mailSenderService;
    @Autowired
    UserrRepository userrRepository;
    @Autowired
    SupplierRequestRepository supplierRequestRepository;

    @Autowired
    SessionService sessionService;


    @Override
    public Product create(Product p, String storeName) throws Exception {
        if (p.getProductCategory() == null)
            throw new Exception("Missing Category");
        //User user = sessionService.getUserBySession();


//        Store store = storeRepository.findByNameAndSeller(storeName, user);
//        if (store == null)
//            throw new Exception("Store not found");

        if (p.getProductWeight() <= 1) {
            p.setDeliveryPrice(6);
        } else if (p.getProductWeight() > 1) {
            p.setDeliveryPrice(6 + (p.getProductWeight() - 1) * 2.5f);

        }
        p.setProductPriceBeforeDiscount(p.getProductPrice());

        Random random = new Random();
        int nbRand = random.nextInt(99999);
        p.setReference(("REF-" + p.getStore().getName().substring(p.getStore().getName().length() - 2) + p.getProductCategory().getName().substring(0, 2).toUpperCase() + p.getName().substring(0, 2).toUpperCase() + nbRand));

        p.setProductStatus(ProductStatus.PENDING);
        p.setCreationDate(new Timestamp(System.currentTimeMillis()));

        // p.setStore(store);

        return productRepository.saveAndFlush(p);
    }

    @Override
    public Product update(Product p) throws Exception {

        if (p.getProductCategory() == null)
            throw new Exception("Missing Category");


        if (p.getProductWeight() <= 1) {
            p.setDeliveryPrice(6);
        } else if (p.getProductWeight() > 1) {
            p.setDeliveryPrice(6 + (p.getProductWeight() - 1) * 2.5f);

        }
        p.setProductPriceBeforeDiscount(p.getProductPrice());


        if (p.getCreationDate() == null)
            p.setCreationDate(new Timestamp(System.currentTimeMillis()));


        return productRepository.saveAndFlush(p);
    }

    @Override
    public List<Product> retrieveAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        if (productRepository.findById(id).isPresent()) {
            return productRepository.findById(id).get();
        }

        return new Product();
    }

    @Override
    public void delete(Product p) {
        productRepository.delete(p);
    }

//    @Override
//    public Product createAndAssignToStore(Product p, Long idStore) {
//        Store store = storeService.getById(idStore);
//        p.setStore(store);
//        return this.create(p);
//
//    }

    @Override
    public Product createAndAssignCategoryAndSubCategory(Product p, String categoryName, String subCatName, Set<String> storeName) throws Exception {
        ProductCategory category = productCategoryRepository.findByName(categoryName);
        ProductCategory subCategory = productCategoryRepository.findByNameAndCategoryName(subCatName, categoryName);
        User user = sessionService.getUserBySession();

        if (category == null && subCategory == null) {
            ProductCategory category1 = new ProductCategory();
            category1.setName(categoryName);
            subCategory = new ProductCategory();
            subCategory.setName(subCatName);
            subCategory.setCategory(category1);
            productCategoryRepository.save(subCategory);

        }
        if (category != null && subCategory == null) {
            subCategory = new ProductCategory();
            subCategory.setName(subCatName);
            productCategoryRepository.save(subCategory);
            subCategory.setCategory(category);
            productCategoryRepository.save(category);


        }


        //cascade
        p.setProductCategory(subCategory);
        for (String st : storeName.stream().toList()) {
            Product p2 = new Product();
            Store store = storeRepository.findByNameAndSeller(st, user);
            if (store == null)
                throw new Exception("Store not found");
            p2.setStore(store);
            p2.setName(p.getName());
            p2.setProductWeight(p.getProductWeight());
            p2.setProductPrice(p.getProductPrice());
            p2.setDescription(p.getDescription());
            p2.setQuantity(p.getQuantity());
            p2.setAdditionalDeliveryInstructions(p.getAdditionalDeliveryInstructions());
            p2.setImage(p.getImage());
            p2.setImage1(p.getImage1());
            p2.setImage2(p.getImage2());
            p2.setImage3(p.getImage3());
            p2.setProductCategory(p.getProductCategory());


            this.create(p2, st);
        }

        return p;
    }

    @Override
    public ByteArrayInputStream allSupplierRequestsOnProduct(Long id, String suppStatus) {

        Product product = productRepository.findById(id).get();
        List<SupplierRequest> supplierRequests = supplierRequestRepository.findSupplierRequestsByProductId(id);
        if (suppStatus.equals("DELIVERED"))
            supplierRequests = supplierRequests.stream().filter(s -> s.getRequestStatus().equals(SupplierRequestStatus.DELIVERED)).toList();
        if (suppStatus.equals("ACCEPTED"))
            supplierRequests = supplierRequests.stream().filter(s -> s.getRequestStatus().equals(SupplierRequestStatus.ACCEPTED)).toList();
        if (suppStatus.equals("PENDING"))
            supplierRequests = supplierRequests.stream().filter(s -> s.getRequestStatus().equals(SupplierRequestStatus.WAITING_FOR_VALIDATION)).toList();
        if (suppStatus.equals("REJECTED"))
            supplierRequests = supplierRequests.stream().filter(s -> s.getRequestStatus().equals(SupplierRequestStatus.REJECTED)).toList();

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            com.itextpdf.text.pdf.PdfWriter.getInstance(document, out);
            document.open();

            // Add Text to PDF file ->
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.DARK_GRAY);
            Font small = FontFactory.getFont(FontFactory.COURIER, 9, BaseColor.BLUE);

            Paragraph para = new Paragraph("Product " + product.getName().toUpperCase() + " supplier requests", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(6);
            // Add PDF Table Header ->
            Stream.of("REF", "Supplier", "Quantity", "Unity Price", "Shipping date", "Status")
                    .forEach(headerTitle -> {
                        PdfPCell header = new PdfPCell();
                        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                        ;
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setHorizontalAlignment(Element.ALIGN_CENTER);
                        header.setBorderWidth(1);
                        header.setBorderColor(BaseColor.BLACK);
                        header.setPhrase(new Phrase(headerTitle, headFont));
                        table.addCell(header);
                    });

            for (SupplierRequest supp : supplierRequests) {

                //String charOnly= stat.replaceAll("[^A-z]", "");
                PdfPCell ref = new PdfPCell(new Phrase(supp.getReference()));
                ref.setPaddingLeft(4);
                ref.setVerticalAlignment(Element.ALIGN_MIDDLE);
                ref.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(ref);

                PdfPCell supplier = new PdfPCell(new Phrase(supp.getSupplier().getBrandName()));
                supplier.setPaddingLeft(4);
                supplier.setVerticalAlignment(Element.ALIGN_MIDDLE);
                supplier.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(supplier);

                PdfPCell quatity = new PdfPCell(new Phrase(String.valueOf(supp.getQuantity())));
                quatity.setPaddingLeft(4);
                quatity.setVerticalAlignment(Element.ALIGN_MIDDLE);
                quatity.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(quatity);

                PdfPCell unityPrice = new PdfPCell(new Phrase(String.valueOf(supp.getUnityPrice())));
                unityPrice.setPaddingLeft(4);
                unityPrice.setVerticalAlignment(Element.ALIGN_MIDDLE);
                unityPrice.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(unityPrice);

                PdfPCell shippingDate = new PdfPCell(new Phrase(String.valueOf(supp.getDeliveryDate())));
                shippingDate.setPaddingLeft(4);
                shippingDate.setVerticalAlignment(Element.ALIGN_MIDDLE);
                shippingDate.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(shippingDate);

                PdfPCell status = new PdfPCell(new Phrase(String.valueOf(supp.getRequestStatus())));
                status.setPaddingLeft(4);
                status.setVerticalAlignment(Element.ALIGN_MIDDLE);
                status.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(status);
            }

            Paragraph p = new Paragraph("\n" + new Date(System.currentTimeMillis()), small);
            document.add(table);
            document.add(p);
            document.close();


        } catch (DocumentException e) {
            logger.error(e.toString());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public List<Product> getProductBySeller() {
        User user = sessionService.getUserBySession();
        List<Product> products = new ArrayList<>();
        for (Store s : user.getStores()) {
            products.addAll(productRepository.findProductsByStore(s));
        }
        return products;
    }

    @Override
    public List<Product> getProductsByStore(String store) {
        User user = sessionService.getUserBySession();
        Store store1 = storeRepository.findByNameAndSeller(store, user);
        return new ArrayList<>(store1.getProducts());
    }

    @Override
    public List<Product> getProductsOutOfStockBySeller() {
        User user = sessionService.getUserBySession();
        List<Product> products=new ArrayList<>();
        List<Store> stores = storeService.getStoresByUser(user.getId());
        for (Store s : stores) {
            for(Product p:s.getProducts()){
                if(p.getQuantity()<=0)
                    products.add(p);
            }
        }
        return products;
    }


    @Scheduled(cron = "0 0 11 1 * *")
    void productsOutOfStock() {
        List<User> userList = userrRepository.findUserByRoleType(RoleType.SELLER);
        for (User user : userList) {
            StringBuilder s = new StringBuilder("Products Out Of Stock");
            int i = 0;
            for (Store store : user.getStores()) {
                s.append("\n").append("STORE : ").append(store.getName());
                for (Product p : store.getProducts()) {
                    if (p.getQuantity() == 0) {
                        s.append("\n").append("   ").append(p.getReference()).append(" : ").append(p.getName());
                        i++;

                    }
                }
            }
            if (i > 0) {
                mailSenderService.sendEmail(user.getEmail(), "Products Out Of Stock", s.toString());
                log.info(s.toString());
            } else {
                log.info("no products out of stock");
            }
        }
    }

    @Override
    public List<SupplierRequest> retriveRequestsByProduct(Long idProduct) {
        return productRepository.findById(idProduct).get().getSupplierRequests()
                .stream().filter(s -> s.getRequestStatus().equals(SupplierRequestStatus.WAITING_FOR_VALIDATION)).toList();

    }

}










