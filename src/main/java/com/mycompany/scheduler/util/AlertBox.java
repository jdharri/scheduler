
package com.mycompany.scheduler.util;

import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author jdharri
 */
public class AlertBox {
    public static void show(final String title, final String message){
        Stage s = new Stage();
        s.initModality(Modality.APPLICATION_MODAL);
    }
}
