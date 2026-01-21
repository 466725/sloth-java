package tutorial.annotation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

class ApplyAnno {
    protected final static Logger logger = LogManager.getLogger(ApplyAnno.class.getName());

    @AnnoDemo(intValue = 64393733, strValue = "Weipeng's AnnoDemo, have fun! ")
    public void applyAnnotation() {
        logger.info("Applying value to Weipeng's Annodemo. ");
    }
}
