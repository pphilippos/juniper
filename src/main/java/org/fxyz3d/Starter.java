/*
 * Copyright (C) 2025  Philippos Papaphilippou
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * 
 */
package org.fxyz3d; // (official way of using FXyz)

import javafx.application.Application;
import javafx.stage.Stage;

/** 
 * Helper class for starting the application internally (Maven is responsible for launching it anyway).
 *
 * @author Philippos Papaphilippou
 */
public class Starter extends Application  {
    @Override
    public void start(Stage primaryStage) { }
    
    public static void main(String[] args) {
        Juniper j = new Juniper();   
        j.main(args);
        System.exit(0);
    }
}
