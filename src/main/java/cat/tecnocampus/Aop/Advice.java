package cat.tecnocampus.Aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;



@Aspect
@Component
public class Advice {

    private static boolean runAround = true;

    private static final Logger logger = LoggerFactory.getLogger(Advice.class);

    //Create a pointcut for all methods (there is only one) that have a single attribute of class Classroom
    @Pointcut("execution(* cat.tecnocampus.application.ControllerDAO.*(..))")
    public void pointcutSingleAttribute() { }

    //Create a before advice for the first pointcut that logs the message "Working with a classroom"
    @Before("pointcutSingleAttribute()")
    public void beforeAllMethods(){
        logger.info("Working with a classroom");
    }

    //Create a pointcut for all methods that begins with the word find
    @Pointcut("execution(* cat.tecnocampus.application.ControllerDAO.find*(..))")
    public void pointcutBeginsWordFind() {}

    //Create a after advice for the second pointcut that logs the message "Finding classrooms"
    @After("pointcutBeginsWordFind()")
    public void afterMethodsWordFind() {
        logger.info("Finding classrooms");
    }

    //Create a pointcut fot the method insertBatch
    @Pointcut("execution(* cat.tecnocampus.application.ControllerDAO.insertBatch(..))")
    public void pointcutinsertBatch() {}

    //Create an around advice for the third pointcut that logs two messages.
    // The one before calling the method that reads before multiple insert and the second after
    // calling the method that reads after multiple insert. Note that you must find the way to pass the list
    // of classrooms to the adviced method.
    @Around("pointcutinsertBatch()") //https://stackoverflow.com/questions/18016503/aspectj-around-and-proceed-with-before-after
    public void aroundinsertBatch(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("before multiple insert " + joinPoint);
        if (runAround) {
            joinPoint.proceed(); System.out.println("insertBatch");
        }
        logger.info("after multiple insert " + joinPoint);
    }


}
