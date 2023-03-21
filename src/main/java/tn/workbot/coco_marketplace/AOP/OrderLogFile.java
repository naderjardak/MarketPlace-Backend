package tn.workbot.coco_marketplace.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OrderLogFile {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* tn.workbot.coco_marketplace.services.interfaces.OrderInterface.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        logger.info("Entering method: {}", joinPoint.getSignature().getName());
    }

    @After("execution(* tn.workbot.coco_marketplace.services.interfaces.OrderInterface.*(..))")
    public void logMethodExit(JoinPoint joinPoint) {
        logger.info("Exiting method: {}", joinPoint.getSignature().getName());
    }

    @Before("execution(* tn.workbot.coco_marketplace.services.interfaces.OrderInterface.endCommandProsess(..))")
    public void logEndCommandProsessEntry(JoinPoint joinPoint) {
        logger.debug("Starting payment process");
    }

    @After("execution(* tn.workbot.coco_marketplace.services.interfaces.OrderInterface.endCommandProsess(..))")
    public void logEndCommandProsessExit(JoinPoint joinPoint) {
        logger.debug("Payment process complete");
    }

    @Before("execution(* tn.workbot.coco_marketplace.services.interfaces.OrderInterface.deleteOrder(..))")
    public void logDeleteOrderEntry(JoinPoint joinPoint) {
        logger.debug("Deleting order with ID: {}", joinPoint.getArgs()[0]);
    }

    @Before("execution(* tn.workbot.coco_marketplace.services.interfaces.OrderInterface.researchProduct(..))")
    public void logResearchProductEntry(JoinPoint joinPoint) {
        logger.debug("Searching for products with criteria: maxPrix={}, minPrix={}, nameProduct={}, categorie={}, mark={}, productFiltre={}",
                joinPoint.getArgs()[0], joinPoint.getArgs()[1], joinPoint.getArgs()[2], joinPoint.getArgs()[3], joinPoint.getArgs()[4], joinPoint.getArgs()[5]);
    }

}
