/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 *
 * @author xen
 */

/* ImageFilter.java is used by FileChooserDemo2.java. */
public class ImageFilter extends FileFilter {

    //Accept all directories and all gif, jpg, tiff, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = ImageExtensions.getExtension(f);
        if (extension != null) {
            if (/*extension.equals(ImageExtensions.tiff)
                    || extension.equals(ImageExtensions.tif)
                    || */
                     extension.equals(ImageExtensions.jpeg)
                    || extension.equals(ImageExtensions.jpg)
                    ) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "*.jpeg, *.jpg";
    }

}
