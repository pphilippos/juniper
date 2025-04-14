/*
 * Copyright (C) 2025  Philippos Papaphilippou
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 * 
 */
package org.fxyz3d; // (official way of using FXyz)

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;

import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Pair;
import javax.imageio.ImageIO;

import org.fxyz3d.importers.maya.Xform;
import org.fxyz3d.shapes.primitives.Text3DMesh;

/** 
 * This class provides the main window, where the stage displays the generated 3D scatterplot
 *
 * @author Philippos Papaphilippou
 */
public class Juniper extends Application {
    
    // Initialise the first groups inside the Node hierarchy (Xforms are also Groups) 
    final Group root = new Group();
    final Xform world = new Xform();
    final Xform axisGroup = new Xform();
    final Xform axismGroup = new Xform();
    final Xform[] ticsGroups = {new Xform(), new Xform(), new Xform()};
    final Xform[] ticMarksGroups = {new Xform(), new Xform(), new Xform(), new Xform(), new Xform(), new Xform(), new Xform(), new Xform()};
    final Xform[] gridLinesGroups = {new Xform(), new Xform(), new Xform(), 
                                    new Xform(), new Xform(), new Xform(), new Xform(),
                                    new Xform(), new Xform(), new Xform(), new Xform()};
    final Xform[] gridSurfGroups = {new Xform(), new Xform(), new Xform()};
    final Xform plotw = new Xform();
    
    final Xform points = new Xform();
    final Xform point_labels = new Xform();
    final Xform[] point_shadows = {new Xform(), new Xform(), new Xform(), new Xform(), new Xform()};
    final Xform[] point_shadow_projections = {new Xform(), new Xform(), new Xform(), new Xform(), new Xform()};
    final Xform fog = new Xform();

    // Camera initialisation
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform(); 
    final Xform cameraXform3 = new Xform();
        
    // Static values relating to camera positioning, mouse handling (consistent with the official JavaFX tutorials)
    public static double CAMERA_INITIAL_DISTANCE = -900;
    public static double CAMERA_DISTANCE = -900;
    private static final double CAMERA_INITIAL_X_ANGLE = 20.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 120.0;
    private static final double CAMERA_INITIAL_HEIGHT = -30.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;    
    
    private static final double CONTROL_MULTIPLIER = 0.1, SHIFT_MULTIPLIER = 10.0,    
                      MOUSE_SPEED = 0.1, ROTATION_SPEED = 2.0, TRACK_SPEED = 0.3;
    
    public static final double AXIS_LENGTH = 250.0;
    
    double mousePosX, mousePosY, mouseOldX, mouseOldY, mouseDeltaX, mouseDeltaY;   
    
    double pointsize = 5;
    boolean draw = false;
    boolean screenshot = false;
    int pointtype = 1;
    int dimensions = 7;
    boolean depth_colormap = false;
    
    double rotation_remainder = 0;
    double deceleration = 1;
    
    double xlabel_dis = 0.15, ylabel_dis = 0.15, zlabel_dis = 0.15;
    double xtic_dis = 0.05, ytic_dis = 0.05, ztic_dis = 0.05;
    double[] max_width = {0,0,0};
    double tic_mark_perc = 0.01;
    double grid_opacity=0.33;
    double shadow_opacity=0.33;
    
    Text3DMesh xlabel, ylabel, zlabel;
    Rectangle xlabel_b, ylabel_b, zlabel_b;
    
    Rotate label_heading=new Rotate(), label_roll=new Rotate();
    
    Cylinder zm, zm2, zm3, xm, ym;
    PhongMaterial redMaterial, greenMaterial,blueMaterial, grayMaterial, gridMaterial, gridLinesMaterial, shadowMaterial;
    
    Map<Double,Pair<String, Group>> xtics, ytics, ztics;
    Map[] tics = {xtics, ztics, ytics}; 
    
    private Controls child;
    Text text;
    
    double last_time = 0, last_time2 = 0;

    double rot;
    
    // https://stackoverflow.com/questions/58388279/sorting-a-pairstring-integer-in-java
    Comparator<Pair<Double, Integer>> pairComparator = Comparator.<Pair<Double, Integer>, Double>comparing(Pair::getKey).thenComparingInt(Pair::getValue);
    
    public static final boolean fps_print = false;
    int fps_frames = 0;
    double fps_time = 0;
    
    @Override
    public void start(Stage primaryStage) {     
        System.out.println("Starting");
        //System.setProperty("prism.forceGPU","true");
        //System.setProperty("prism.order", "opengl");
        buildCamera();
        buildAxes();   
        
        Scene scene = new Scene(root, 970, 600, true, SceneAntialiasing.BALANCED);//.DISABLED);
        scene.setFill(Color.WHITE/*LIGHTGRAY*/);
        
        handleKeyboard(scene, world);
        handleMouse(scene, world);
        
        primaryStage.setTitle("Juniper");
        primaryStage.setScene(scene);
        primaryStage.show();
       
        scene.setCamera(camera);
        root.getChildren().add(world);
        world.setTx(-AXIS_LENGTH/2);
        world.setTy(-AXIS_LENGTH/2);
        world.setTz(-AXIS_LENGTH/2);        
        world.getChildren().add(plotw);
        world.getChildren().add(point_labels);
        
        for (int ax=0; ax<point_shadows.length; ax++){
            world.getChildren().add(point_shadow_projections[ax]);
            point_shadow_projections[ax].setVisible(false);
        }
        for (int ax=0; ax<point_shadows.length; ax++){
            world.getChildren().add(point_shadows[ax]);
            point_shadows[ax].setVisible(false);
        }       

        // Axis labels
        xlabel = new Text3DMesh("x label", "Liberation Serif", 25, true);  xlabel.setHeight(0);
        ylabel = new Text3DMesh("y label", "Liberation Serif", 25, true);  ylabel.setHeight(0); 
        zlabel = new Text3DMesh("z label", "Liberation Serif", 25, true);  zlabel.setHeight(0); 
        
        
        xlabel.setScaleX(0.8);  xlabel.setScaleY(0.8);
        ylabel.setScaleX(0.8);  ylabel.setScaleY(0.8);
        zlabel.setScaleX(0.8);  zlabel.setScaleY(0.8);
        
        // Axis labels center of "gravity" for rotations
        xlabel.setTranslateX(-xlabel.getLayoutBounds().getCenterX());
        xlabel.setTranslateY(-xlabel.getLayoutBounds().getCenterY());
        
        ylabel.setTranslateX(-ylabel.getLayoutBounds().getCenterX());
        ylabel.setTranslateY(-ylabel.getLayoutBounds().getCenterY());
        
        zlabel.setTranslateX(-zlabel.getLayoutBounds().getCenterX());
        zlabel.setTranslateY(-zlabel.getLayoutBounds().getCenterY());
        zlabel.setRotate(270);
           
        xlabel.setTextureModeNone(Color.DARKRED);//CRIMSON
        ylabel.setTextureModeNone(Color.DARKBLUE);
        zlabel.setTextureModeNone(Color.DARKGREEN);
        
        
        // Axis label rotation groups
        Group xlabel_rg = new Group(); xlabel_rg.getChildren().add(xlabel); 
        Group ylabel_rg = new Group(); ylabel_rg.getChildren().add(ylabel); 
        Group zlabel_rg = new Group(); zlabel_rg.getChildren().add(zlabel);
        world.getChildren().addAll(xlabel_rg, ylabel_rg, zlabel_rg);
        
        // Draw invisible borders to be shown on demand
        xlabel_b = new Rectangle();
        xlabel_b.setFill(Color.WHITESMOKE);
        xlabel_b.setWidth(xlabel.getLayoutBounds().getWidth());
        xlabel_b.setHeight(xlabel.getLayoutBounds().getHeight());  
        xlabel_b.setTranslateX(-xlabel.getLayoutBounds().getWidth()/2);
        xlabel_b.setTranslateY(-xlabel.getLayoutBounds().getHeight()/2);
        xlabel_b.setTranslateZ(3);
        xlabel_b.setVisible(false);
        xlabel_rg.getChildren().add(xlabel_b);
        
        ylabel_b = new Rectangle();
        ylabel_b.setFill(Color.WHITESMOKE);
        ylabel_b.setWidth(ylabel.getLayoutBounds().getWidth());
        ylabel_b.setHeight(ylabel.getLayoutBounds().getHeight());  
        ylabel_b.setTranslateX(-ylabel.getLayoutBounds().getWidth()/2);
        ylabel_b.setTranslateY(-ylabel.getLayoutBounds().getHeight()/2);
        ylabel_b.setTranslateZ(3);
        ylabel_b.setVisible(false);
        ylabel_rg.getChildren().add(ylabel_b);
        
        zlabel_b = new Rectangle();
        zlabel_b.setFill(Color.WHITESMOKE);
        zlabel_b.setWidth(zlabel.getLayoutBounds().getWidth());
        zlabel_b.setHeight(zlabel.getLayoutBounds().getHeight());  
        zlabel_b.setTranslateX(-zlabel.getLayoutBounds().getWidth()/2);
        zlabel_b.setTranslateY(-zlabel.getLayoutBounds().getHeight()/2);
        zlabel_b.setTranslateZ(3);
        zlabel_b.setRotate(90);
        zlabel_b.setVisible(false);
        zlabel_rg.getChildren().add(zlabel_b);
                
        label_heading.setAxis(new Point3D(0,1,0)); 
        label_roll.setAxis(new Point3D(1,0,0));         
                
        xlabel_rg.getTransforms().addAll(label_heading, label_roll);
        ylabel_rg.getTransforms().addAll(label_heading, label_roll);
        zlabel_rg.getTransforms().addAll(label_heading, label_roll);
        
        // Reposition labels
        xlabel_rg.setTranslateX(AXIS_LENGTH/2);
        xlabel_rg.setTranslateY(- AXIS_LENGTH*xlabel_dis);
        xlabel_rg.setTranslateZ(- AXIS_LENGTH*xlabel_dis);        
        ylabel_rg.setTranslateZ(AXIS_LENGTH/2);
        ylabel_rg.setTranslateY(- AXIS_LENGTH*ylabel_dis);
        ylabel_rg.setTranslateX(- AXIS_LENGTH*ylabel_dis);
        zlabel_rg.setTranslateY(AXIS_LENGTH/2);
        zlabel_rg.setTranslateZ(- AXIS_LENGTH*zlabel_dis);
        zlabel_rg.setTranslateX(- AXIS_LENGTH*zlabel_dis);

        // Tics
        for (int ax=0; ax<3; ax+=1){
            tics[ax] = new HashMap();
        }
        drawTics();
        
        world.getChildren().remove(fog);
        world.getChildren().add(fog);       
        
        child = new Controls();
        child.setVisible(true);
        child.parent = this;
       
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Print FPS in standard output on every 1000 frames
                if (fps_print){
                fps_frames +=1;
                    if (fps_frames==1000){                    
                        double time = System.currentTimeMillis();
                        System.out.println("FPS: " + 1000/(time-fps_time)*1000);
                        fps_frames=0;
                        fps_time = time;
                    }
                }
                    
                if (rotation_remainder!=0){
                    double time = System.currentTimeMillis();
                    //if (1/((time-last_time2)/1000)<=120) // vsync equivalent?
                    {
                        double step = (time-last_time)/16;
                        cameraXform.ry.setAngle(cameraXform.ry.getAngle()-rotation_remainder*step);  
                        if (deceleration!=1)
                            rotation_remainder = rotation_remainder*Math.pow(deceleration, step/2);
                        if (Math.abs(rotation_remainder)<0.01)
                            rotation_remainder=0;
                        seeLabels();   
                        last_time2 = time; 
                    }
                    last_time = time;                    
                }
                if (screenshot){
                    WritableImage imageS = new WritableImage(Integer.parseInt(child.picXdimSpinner.getValue().toString()),Integer.parseInt(child.picYdimSpinner.getValue().toString()));
                    WritableImage image = primaryStage.getScene().snapshot(imageS); 
                    BufferedImage b = SwingFXUtils.fromFXImage(image, null);
                    
                    try { ImageIO.write(b, "png", child.screenshotf);} catch (IOException e) { }
                    screenshot = false;                      
                }                
            }            
        }.start();
        
    }
 
    /**
     * This function is called whenever there is a change to the point of view, 
     * change to the data, or any other occasion where the labels may need to be
     * adjusted to face the camera. This creates a 2D overlay effect, while the labels
     * are still in the 3D space. It is also responsible for hiding ticks etc., 
     * when a rotation brings them behind other objects.
     */
    public void seeLabels(){
        // Get camera heading and roll angles
        double hl_angle =     cameraXform.ry.getAngle()+180;
        double rl_angle = 180-cameraXform.rx.getAngle();
        
        // Apply to all labels (inheriting same transformations)
        label_heading.setAngle(hl_angle);  label_roll.setAngle(rl_angle);
                
        rot = (360+cameraXform.ry.getAngle()%360)%360;
        
        // Adjust tick visibility according to the camera rotation angle (heading only) 
        boolean tics_visible = ticMarksGroups[0].isVisible();
        zm.setVisible(!(rot>180 && rot<270));  ticMarksGroups[6].setVisible(tics_visible && !(rot>180 && rot<270));
        zm2.setVisible(!(rot>270 ));           ticMarksGroups[7].setVisible(tics_visible && !(rot>270));
        zm3.setVisible(!(rot>90 && rot<180));  ticMarksGroups[5].setVisible(tics_visible && !(rot>90 && rot<180));
        
        // Same for grids and shadows, but first use their current location for simplicity
        boolean gridl_visible_x = gridLinesGroups[0].isVisible();
        boolean gridl_visible_z = gridLinesGroups[1].isVisible()||gridLinesGroups[7].isVisible();
        boolean gridl_visible_y = gridLinesGroups[2].isVisible();
        boolean shadowy_visible = point_shadows[1].isVisible() || point_shadows[3].isVisible();
        boolean shadowx_visible = point_shadows[2].isVisible() || point_shadows[4].isVisible();
        boolean shadowpy_visible = point_shadow_projections[1].isVisible() || point_shadow_projections[3].isVisible();
        boolean shadowpx_visible = point_shadow_projections[2].isVisible() || point_shadow_projections[4].isVisible();
        
        // Rotation intervals are different to tics, e.g. shadows (projections) must remain visible on the back surfaces.
        gridLinesGroups[3].setVisible(gridl_visible_x && ((rot>90 && rot<270)));
        gridLinesGroups[8].setVisible(gridl_visible_z && ((rot>90 && rot<270)));
        point_shadows[2].setVisible(shadowx_visible && (rot>90 && rot<270));
        point_shadow_projections[2].setVisible(shadowpx_visible && (rot>90 && rot<270));
        
        gridLinesGroups[4].setVisible(gridl_visible_x && (!(rot>90 && rot<270)));
        gridLinesGroups[9].setVisible(gridl_visible_z && (!(rot>90 && rot<270)));
        point_shadows[4].setVisible(shadowx_visible && !(rot>90 && rot<270));
        point_shadow_projections[4].setVisible(shadowpx_visible && !(rot>90 && rot<270));
        
        gridLinesGroups[5].setVisible(gridl_visible_y && ((rot>180)));
        gridLinesGroups[1].setVisible(gridl_visible_z && ((rot>180)));
        point_shadows[1].setVisible(shadowy_visible && (rot>180));
        point_shadow_projections[1].setVisible(shadowpy_visible && (rot>180));
        
        gridLinesGroups[6].setVisible(gridl_visible_y && ((rot<180)));
        gridLinesGroups[7].setVisible(gridl_visible_z && ((rot<180)));
        point_shadows[3].setVisible(shadowy_visible && (rot<180));
        point_shadow_projections[3].setVisible(shadowpy_visible && (rot<180));
       
        // For every axis, (the labels and ticks need to be moved to the (mirror) axis closer to the camera)
        for (int ax=0; ax<3; ax+=1){
            int mirrored_z = 0;
            int mirrored_x = 0;
                      
            Node gtmp=xlabel.getParent();
            switch (ax){
                case 1: gtmp=zlabel.getParent(); break;
                case 2: gtmp=ylabel.getParent(); break; 
            }
            
            // See which displacement is needed to the corresponding labels 
            switch (ax){
                case 0: mirrored_z = (rot>90 && rot<270)? 
                            (gtmp.getTranslateZ()<AXIS_LENGTH/2?1:0) :
                            (gtmp.getTranslateZ()>AXIS_LENGTH/2?-1:0); break;
                case 1: mirrored_z = (!(rot>0 && rot<180))?
                            (gtmp.getTranslateZ()<AXIS_LENGTH/2?1:0) :
                            (gtmp.getTranslateZ()>AXIS_LENGTH/2?-1:0); 
                        mirrored_x = (!(rot>90 && rot<270))?
                            (gtmp.getTranslateX()<AXIS_LENGTH/2?1:0) :
                            (gtmp.getTranslateX()>AXIS_LENGTH/2?-1:0); break;
                case 2: mirrored_x = (!(rot>0 && rot<180))? 
                            (gtmp.getTranslateX()<AXIS_LENGTH/2?1:0) :
                            (gtmp.getTranslateX()>AXIS_LENGTH/2?-1:0); break;
            }
            
            // Apply accordingly 
            if (mirrored_z==1) { 
                gtmp.setTranslateZ(-gtmp.getTranslateZ()+AXIS_LENGTH);
                if (ax==0) xm.setTranslateZ(0);
            }
            if (mirrored_z==-1){ 
                gtmp.setTranslateZ(-(gtmp.getTranslateZ()-AXIS_LENGTH));
                if (ax==0) xm.setTranslateZ(AXIS_LENGTH);
            }
            if (mirrored_x==1) { 
                gtmp.setTranslateX(-gtmp.getTranslateX()+AXIS_LENGTH);
                if (ax==2) ym.setTranslateX(-AXIS_LENGTH);
            }
            if (mirrored_x==-1){ 
                gtmp.setTranslateX(-(gtmp.getTranslateX()-AXIS_LENGTH));
                if (ax==2) ym.setTranslateX(0);
            }
            
            
            // Equivalent procedure for the tics corresponding to the axis in question
            boolean tic_hdir = false;
            switch (ax){
                case 0: tic_hdir = (rot>90 && rot<270)^(!(rot>0 && rot<180)); break;
                case 2: tic_hdir = !((rot>90 && rot<270)^(!(rot>0 && rot<180))); break;
            }
            
            gtmp = ticsGroups[ax];
            switch (ax){
                case 0: mirrored_z = (rot>90 && rot<270)? 
                            (gtmp.getTranslateZ()<AXIS_LENGTH/2?1:0) :
                            (gtmp.getTranslateZ()>AXIS_LENGTH/2?-1:0); break;
                case 1: mirrored_z = (!(rot>0 && rot<180))?
                            (gtmp.getTranslateZ()<AXIS_LENGTH/2?1:0) :
                            (gtmp.getTranslateZ()>AXIS_LENGTH/2?-1:0); 
                        mirrored_x = (!(rot>90 && rot<270))?
                            (gtmp.getTranslateX()<AXIS_LENGTH/2?1:0) :
                            (gtmp.getTranslateX()>AXIS_LENGTH/2?-1:0); break;
                case 2: mirrored_x = (!(rot>0 && rot<180))? 
                            (gtmp.getTranslateX()<AXIS_LENGTH/2?1:0) :
                            (gtmp.getTranslateX()>AXIS_LENGTH/2?-1:0); break;
            }
                
            if (mirrored_z==1) { 
                gtmp.setTranslateZ(-gtmp.getTranslateZ()+AXIS_LENGTH);               
            }
            if (mirrored_z==-1){ 
                gtmp.setTranslateZ(-(gtmp.getTranslateZ()-AXIS_LENGTH));                
            }
            if (mirrored_x==1) { 
                gtmp.setTranslateX(-gtmp.getTranslateX()+AXIS_LENGTH);               
            }
            if (mirrored_x==-1){ 
                gtmp.setTranslateX(-(gtmp.getTranslateX()-AXIS_LENGTH));                
            }
            
            // Also, if the tick labels are set to appear horizontally, 
            for (Object tic : tics[ax].keySet()){ 
                Group g = (Group) ((Pair)tics[ax].get(tic)).getValue();               
                
                Node n = g.getChildren().getFirst();                
                if (n.getRotate()!=0){
                    
                     // mirror them when they are in the correct "half" of the plot 
                    if(tic_hdir) // (consistent across the axis)
                       n.setRotate(90);            
                    else                      
                       n.setRotate(270);
                }
                   
            }      
        }
        
        // Resort the elements to help with the transparency issue, just in case 
        // the rotation brings something transparent in front of other elements
        Xform[] to_sort = new Xform[1];
        int i_=0;
        for (int ax=0; ax<point_shadows.length; ax++){
            if (point_shadows[ax].isVisible()){
                to_sort[i_]=point_shadows[ax];
                depth_sort(to_sort);
            }
        }

        for (int ax=0; ax<gridSurfGroups.length; ax++){
            if (gridSurfGroups[ax].isVisible()){               
                to_sort[i_]=gridSurfGroups[ax];
                depth_sort(to_sort);
            }
        }
        
        // Also update the color map, if it is based on the depth from the camera
        if (depth_colormap) {
            depth_color();
        }  
        point_labels.setViewOrder(0);
    }
    
    /**
     * Adjusts the point colors according to the distance from the user (camera),
     * in order to help with depth perception.
     */
    private void depth_color(){
        if (!depth_colormap)
            return;
        
        double min_dis=Double.MAX_VALUE;
        double max_dis=0;
        double[] dists= new double[points.getChildren().size()];

        int dists_i=0;
        // For every point,
        for (Node n: points.getChildren()){
            double dis = depth_tester.get_distance_from_cam(n, cameraXform);
            
            // store the distance,
            dists[dists_i++]=dis;
            if (Double.isNaN(dis)){
                continue;
            }
            // and calculate furthest and nearest point.
            min_dis = Math.min(min_dis, dis);
            max_dis = Math.max(max_dis, dis);
        }

        dists_i=0;
        for (Node n: points.getChildren()){
            double dis = dists[dists_i++];
            double x = (dis-min_dis)/(max_dis-min_dis);
            PhongMaterial m = (PhongMaterial) ((Shape3D) n).getMaterial();
            javafx.scene.paint.Color c = m.getDiffuseColor();
            
            // Translate the relative distance into the viridis color palette.
            double r,g,b,a;
            r= x*x*x*-1215.35 + x*x*2144.48 + x*-733.556 + 60.4242; 
            g= x*x*x* 548.202 + x*x*-960.762+ x* 620.136 + 38.2424;
            b= x*x*x* 837.818 + x*x*-1588.92 + x*625.281 + 146.576;
            r=Math.floor(Math.min(255,Math.max(0,r)))/255;
            g=Math.floor(Math.min(255,Math.max(0,g)))/255;
            b=Math.floor(Math.min(255,Math.max(0,b)))/255;
            a=c.getOpacity();
            m=new PhongMaterial();
            m.setDiffuseColor(new Color(r,g,b,a));
            
            // Apply to object (point).
            ((Shape3D) n).setMaterial(m);                 
        }
        
        // Same colours also the the shadows (projections) of the points
        for (int ax=0; ax<point_shadow_projections.length; ax++){
            if (point_shadow_projections[ax].isVisible()){
                dists_i=0;       
                for (Node n: point_shadow_projections[ax].getChildren()){
                    double dis = dists[dists_i++];
                    double x = (dis-min_dis)/(max_dis-min_dis);

                    double r,g,b,a;
                    r= x*x*x*-1215.35 + x*x*2144.48 + x*-733.556 + 60.4242; 
                    g= x*x*x* 548.202 + x*x*-960.762+ x* 620.136 + 38.2424;
                    b= x*x*x* 837.818 + x*x*-1588.92 + x*625.281 + 146.576;
                    r=Math.floor(Math.min(255,Math.max(0,r)))/255;
                    g=Math.floor(Math.min(255,Math.max(0,g)))/255;
                    b=Math.floor(Math.min(255,Math.max(0,b)))/255;   
                
                    PhongMaterial m = (PhongMaterial) ((Shape3D) n).getMaterial();
                    javafx.scene.paint.Color c = m.getDiffuseColor();
                    a=c.getOpacity();
                                  
                    m=new PhongMaterial();
                    m.setDiffuseColor(new Color(r,g,b,a));
                    ((Shape3D) n).setMaterial(m);      
                    
                    if (Double.isNaN(dis)){
                        ((Shape3D) n).setVisible(false);
                    }
                }
            }
        }    
    }
    
    /**
     * Basic way of building the Camera objects in JavaFX.
     */
    private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);        
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_DISTANCE);

        camera.setCacheHint(CacheHint.SPEED);
        
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
        
        cameraXform2.t.setY(CAMERA_INITIAL_HEIGHT);
    }
    
    /**
     * Generates 3D objects for the ticks (appearing as slanted cylinders), 
     * their grid lines, and their labels with or without rectangles borders.
     * 
     */
    private void drawTics() {
        for (int ax=0; ax<gridLinesGroups.length; ax+=1)
            gridLinesGroups[ax].getChildren().clear();            
        for (int ax=0; ax<ticMarksGroups.length; ax+=1)
            ticMarksGroups[ax].getChildren().clear();
        
        // For every axis,
        for (int ax=0; ax<3; ax+=1){            
            ticsGroups[ax].getChildren().clear();
            gridSurfGroups[ax].getChildren().clear();
            
            // For every tick (position is precalculated)
            for (Object i_: tics[ax].keySet()){ 
                double i = (double) i_;
                
                // Create an FXyz 3D mesh for the text
                Text3DMesh tm = new Text3DMesh((String)((Pair)tics[ax].get(i)).getKey(), "Liberation Serif", 25, true); 
                tm.setHeight(0);
                tm.setTextureModeNone(Color.LIGHTGRAY);
                tm.setScaleX(0.8*(int)child.lsize.getValue()/12.0);  tm.setScaleY(0.8*(int)child.lsize.getValue()/12.0);
                tm.setTranslateX(-tm.getLayoutBounds().getCenterX());
                tm.setTranslateY(-tm.getLayoutBounds().getCenterY());

                // Prepare rectangles as grid surfaces 
                Rectangle grid_surf = new Rectangle(AXIS_LENGTH,AXIS_LENGTH);
                grid_surf.setFill(Color.GREY);
                grid_surf.setOpacity(grid_opacity);
                switch(ax){
                    case 0: grid_surf.setTranslateZ(AXIS_LENGTH/2); grid_surf.setRotationAxis(new Point3D(1,0,1)); grid_surf.setRotate(180);break;
                    case 2: grid_surf.setTranslateX(0);  break;
                    case 1: grid_surf.setTranslateZ(AXIS_LENGTH/2); grid_surf.setRotationAxis(new Point3D(1,0,0)); grid_surf.setRotate(90); break;
                }
                gridSurfGroups[ax].getChildren().add(grid_surf);
                
                // Prepare cylidners as grid lines,
                Cylinder grid = new Cylinder(1, AXIS_LENGTH);
                grid.setMaterial(gridLinesMaterial);
                switch(ax){
                    case 0: grid.setTranslateZ(AXIS_LENGTH/2); grid.setRotationAxis(new Point3D(1,0,0)); grid.setRotate(90);  break;
                    case 2: grid.setTranslateX(AXIS_LENGTH/2); grid.setRotationAxis(new Point3D(0,0,1)); grid.setRotate(-90); break;
                    case 1: grid.setTranslateZ(AXIS_LENGTH/2); grid.setRotationAxis(new Point3D(1,0,0)); grid.setRotate(90);  break;
                }
                if ((ax==0) || (ax==2)) gridLinesGroups[ax].getChildren().add(grid);
                
                // The cylinders have a different direction according to the axis 
                if (ax==0){
                    {
                        Cylinder grid_ = new Cylinder(1, AXIS_LENGTH);
                        grid_.setMaterial(gridLinesMaterial);
                        grid_.setTranslateY(AXIS_LENGTH/2);
                        gridLinesGroups[3].getChildren().add(grid_);
                    }
                    {
                        Cylinder grid_ = new Cylinder(1, AXIS_LENGTH);
                        grid_.setMaterial(gridLinesMaterial);
                        grid_.setTranslateY(AXIS_LENGTH/2);
                        grid_.setTranslateZ(AXIS_LENGTH);
                        gridLinesGroups[4].getChildren().add(grid_);
                    }
                }
                if (ax==2){
                    {
                        Cylinder grid_ = new Cylinder(1, AXIS_LENGTH);
                        grid_.setMaterial(gridLinesMaterial);
                        grid_.setTranslateY(AXIS_LENGTH/2);
                        gridLinesGroups[5].getChildren().add(grid_);
                    }
                    {
                        Cylinder grid_ = new Cylinder(1, AXIS_LENGTH);
                        grid_.setMaterial(gridLinesMaterial);
                        grid_.setTranslateY(AXIS_LENGTH/2);
                        grid_.setTranslateX(AXIS_LENGTH);
                        gridLinesGroups[6].getChildren().add(grid_);
                    }
                }
                if (ax==1){                    
                    gridLinesGroups[1].getChildren().add(grid);
                    
                    {
                        Cylinder grid_ = new Cylinder(1, AXIS_LENGTH);
                        grid_.setRotationAxis(new Point3D(1,0,0)); grid_.setRotate(90);
                        grid_.setMaterial(gridLinesMaterial);
                        grid_.setTranslateZ(AXIS_LENGTH/2);
                        grid_.setTranslateX(AXIS_LENGTH);
                        gridLinesGroups[7].getChildren().add(grid_);
                    }
                    {
                        Cylinder grid_ = new Cylinder(1, AXIS_LENGTH);
                        grid_.setMaterial(gridLinesMaterial);
                        grid_.setRotationAxis(new Point3D(0,0,1)); grid_.setRotate(-90);
                        grid_.setTranslateX(AXIS_LENGTH/2);
                        gridLinesGroups[8].getChildren().add(grid_);
                    }
                    {
                        Cylinder grid_ = new Cylinder(1, AXIS_LENGTH);
                        grid_.setMaterial(gridLinesMaterial);
                        grid_.setRotationAxis(new Point3D(0,0,1)); grid_.setRotate(-90);
                        grid_.setTranslateX(AXIS_LENGTH/2);
                        grid_.setTranslateZ(AXIS_LENGTH);
                        gridLinesGroups[9].getChildren().add(grid_);
                    }
                }
                
                // Small cylinder for the tick itself on the axis, with a 45 degree rotation effect
                Cylinder cy = new Cylinder(1, AXIS_LENGTH*tic_mark_perc);
   
                switch(ax){
                    case 0: cy.setRotationAxis(new Point3D(1,0,0)); cy.setRotate(45);   break;
                    case 2: cy.setRotationAxis(new Point3D(0,0,1)); cy.setRotate(-45);   break;
                    case 1: cy.setRotationAxis(new Point3D(-1,0,1)); cy.setRotate(-90); break;
                }
                ticMarksGroups[ax].getChildren().add(cy);
                
                if (ax==0){
                    Cylinder cy_ = new Cylinder(1, AXIS_LENGTH*tic_mark_perc);
                    cy_.setRotationAxis(new Point3D(1,0,0)); cy_.setRotate(45+90); 
                    ticMarksGroups[3].getChildren().add(cy_);
                }
                if (ax==2){
                    Cylinder cy_ = new Cylinder(1, AXIS_LENGTH*tic_mark_perc);
                    cy_.setRotationAxis(new Point3D(0,0,1)); cy_.setRotate(-45+90); 
                    ticMarksGroups[4].getChildren().add(cy_);
                }
                if (ax==1){
                    for (int ax_=5; ax_<8; ax_++){ 
                        Cylinder cy_ = new Cylinder(1, AXIS_LENGTH*tic_mark_perc);
                        cy_.setRotationAxis(new Point3D((ax_%2==1)?1:-1 ,0,1)); cy_.setRotate(-90); 
                        ticMarksGroups[ax_].getChildren().add(cy_);
                    }
                }
                
                // Enclose the label in an invisible border (for now)
                Rectangle re = new Rectangle();
                re.setFill(Color.WHITESMOKE);
                re.setWidth(tm.getLayoutBounds().getWidth());
                re.setHeight(tm.getLayoutBounds().getHeight());  
                re.setTranslateX(-tm.getLayoutBounds().getWidth()/2);
                re.setTranslateY(-tm.getLayoutBounds().getHeight()/2);
                re.setTranslateZ(3); // workaround for overlapping surfaces
                re.setVisible(false);
                
                Group tgr = (Group) ((Pair)tics[ax].get(i)).getValue();
                
                tgr.getChildren().addAll(tm, re);                
                tgr.getTransforms().addAll(label_heading, label_roll);
                ticsGroups[ax].getChildren().add(tgr);
            }
            
            // Group transformations to follow view preferences, such as the distance of the labels from the axes
            double ddis = 0;
            switch(ax){
                case 0: ddis =-AXIS_LENGTH*xtic_dis; break;
                case 2: ddis =-AXIS_LENGTH*ytic_dis; break;
                case 1: ddis =-AXIS_LENGTH*ztic_dis; break;
            }
                    
            ticsGroups[ax].setTranslateX(ddis); 
            ticsGroups[ax].setTranslateY(ddis); 
            ticsGroups[ax].setTranslateZ(ddis);    
            
            switch(ax){
                case 0: ticsGroups[ax].setTranslateX(0); break;
                case 1: ticsGroups[ax].setTranslateY(0); break;
                case 2: ticsGroups[ax].setTranslateZ(0); break;
            }
            
            switch(ax){
                case 0: ticMarksGroups[ax].setTranslateY(-Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2); ticMarksGroups[ax].setTranslateZ(-Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2); break;
                case 1: ticMarksGroups[ax].setTranslateX(-Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2); ticMarksGroups[ax].setTranslateZ(-Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2); break;
                case 2: ticMarksGroups[ax].setTranslateX(-Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2); ticMarksGroups[ax].setTranslateY(-Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2); break;
            }

            if (ax==0){
                ticMarksGroups[3].setTranslateY(-Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2); 
                ticMarksGroups[3].setTranslateZ(AXIS_LENGTH+Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2);
            }
            if (ax==2){
                ticMarksGroups[4].setTranslateX(AXIS_LENGTH+Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2);
                ticMarksGroups[4].setTranslateY(-Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2);
            }
            if (ax==1){
                ticMarksGroups[5].setTranslateX(-Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2); 
                ticMarksGroups[5].setTranslateZ(AXIS_LENGTH+Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2);
                
                ticMarksGroups[6].setTranslateX(AXIS_LENGTH+Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2); 
                ticMarksGroups[6].setTranslateZ(AXIS_LENGTH+Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2);
                
                ticMarksGroups[7].setTranslateX(AXIS_LENGTH+Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2); 
                ticMarksGroups[7].setTranslateZ(-Math.sqrt(2)*AXIS_LENGTH*tic_mark_perc/2/2);
            }
            
            // Move tick objects to the correct location (maybe more tidy outside the axis for loop)
            int j=0;
            for (Object i_: tics[ax].keySet()){
                double i = (double) i_;
                Group tgr = (Group) ((Pair)tics[ax].get(i)).getValue();
               
                switch(ax){
                    case 0: gridLinesGroups[ax].getChildren().get(j).setTranslateX((AXIS_LENGTH)*i); break;
                    case 1: gridLinesGroups[ax].getChildren().get(j).setTranslateY((AXIS_LENGTH)*i); break;
                    case 2: gridLinesGroups[ax].getChildren().get(j).setTranslateZ((AXIS_LENGTH)*i); break;
                }
                switch(ax){
                    case 0: gridSurfGroups[ax].getChildren().get(j).setTranslateX(-AXIS_LENGTH/2+(AXIS_LENGTH)*i); break;
                    case 1: gridSurfGroups[ax].getChildren().get(j).setTranslateY(-AXIS_LENGTH/2+(AXIS_LENGTH)*i); break;
                    case 2: gridSurfGroups[ax].getChildren().get(j).setTranslateZ((AXIS_LENGTH)*i); break;
                }
                if ((i==0) || (i==1.0)){
                    gridSurfGroups[ax].getChildren().get(j).setVisible(false);
                    gridLinesGroups[ax].getChildren().get(j).setVisible(false);
                }
                if (ax==0){
                    gridLinesGroups[3].getChildren().get(j).setTranslateX((AXIS_LENGTH)*i);
                    gridLinesGroups[4].getChildren().get(j).setTranslateX((AXIS_LENGTH)*i);
                }
                if (ax==2){
                    gridLinesGroups[5].getChildren().get(j).setTranslateZ((AXIS_LENGTH)*i);
                    gridLinesGroups[6].getChildren().get(j).setTranslateZ((AXIS_LENGTH)*i);
                }
                if (ax==1){
                    gridLinesGroups[7].getChildren().get(j).setTranslateY((AXIS_LENGTH)*i);
                    gridLinesGroups[8].getChildren().get(j).setTranslateY((AXIS_LENGTH)*i);
                    gridLinesGroups[9].getChildren().get(j).setTranslateY((AXIS_LENGTH)*i);
                    
                }
                
                switch(ax){
                    case 0: tgr.setTranslateX((AXIS_LENGTH)*i); ticMarksGroups[ax].getChildren().get(j).setTranslateX((AXIS_LENGTH)*i); break;
                    case 1: tgr.setTranslateY((AXIS_LENGTH)*i); ticMarksGroups[ax].getChildren().get(j).setTranslateY((AXIS_LENGTH)*i); break;
                    case 2: tgr.setTranslateZ((AXIS_LENGTH)*i); ticMarksGroups[ax].getChildren().get(j).setTranslateZ((AXIS_LENGTH)*i); break;
                }
                
                switch(ax){
                    case 0: ticMarksGroups[3].getChildren().get(j).setTranslateX((AXIS_LENGTH)*i); break;
                    case 1: for (int ax_=5; ax_<8; ax_++) ticMarksGroups[ax_].getChildren().get(j).setTranslateY((AXIS_LENGTH)*i); break;
                    case 2: ticMarksGroups[4].getChildren().get(j).setTranslateZ((AXIS_LENGTH)*i); break;
                }
                j++;
            }
            
        }
        
        // Fog transparency workaround (as last transparency layer since it corresponts to visibility)
        if (fog.isVisible()){
            world.getChildren().remove(fog);
            world.getChildren().add(fog);
        }
        seeLabels();
    }    
    
    
    /**
     * Mostly classical JavaFX code to create the cylinder for axes (inspired by
     * the official MoleculeSampleApp). It also adds the hidden (mirror) axes etc.
     * into the node hierarchy, and builds the fog (as a series of semi-transparent
     * rectangles). 
     */
    private void buildAxes() {
        redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.RED);
        redMaterial.setSpecularColor(Color.DARKRED);
 
        greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.GREEN);
        greenMaterial.setSpecularColor(Color.DARKGREEN);
 
        blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.BLUE);
        blueMaterial.setSpecularColor(Color.DARKBLUE);
 
        grayMaterial = new PhongMaterial();
        grayMaterial.setDiffuseColor(Color.color(0.3,0.3,0.3,0.8));
        grayMaterial.setSpecularColor(Color.color(0.2,0.2,0.2,0.8));
        
        gridMaterial = new PhongMaterial();
        gridMaterial.setDiffuseColor(Color.color(0.5,0.5,0.5,0.4));
        gridMaterial.setSpecularColor(Color.color(0.5,0.5,0.5,0.4));
        
        gridLinesMaterial = new PhongMaterial();
        gridLinesMaterial.setDiffuseColor(Color.color(0.5,0.5,0.5,0.43));
        gridLinesMaterial.setSpecularColor(Color.color(0.5,0.5,0.5,0.43));
        
        shadowMaterial = new PhongMaterial();
        shadowMaterial.setDiffuseColor(Color.color(0.5,0.5,0.5,shadow_opacity));
        shadowMaterial.setSpecularColor(Color.color(0.5,0.5,0.5,shadow_opacity));
        
        final Translate tz = new Translate(AXIS_LENGTH/2-10+5, 0, 0);
        final Translate ty = new Translate(0, AXIS_LENGTH/2-10+5, 0);
        final Translate tx = new Translate(0, 0, AXIS_LENGTH/2-10+5);
        
        final Translate tz2 = new Translate(AXIS_LENGTH/2, 0, AXIS_LENGTH);
        final Translate ty2 = new Translate(AXIS_LENGTH, AXIS_LENGTH/2, AXIS_LENGTH);
        final Translate tx2 = new Translate(AXIS_LENGTH, 0, AXIS_LENGTH/2);
        
        final Translate tz3 = new Translate(AXIS_LENGTH/2, AXIS_LENGTH, 0);
        final Translate ty3 = new Translate(AXIS_LENGTH, AXIS_LENGTH/2, 0);
        final Translate tx3 = new Translate(AXIS_LENGTH, AXIS_LENGTH, AXIS_LENGTH/2);
        
        final Translate tx3c = new Translate(0, AXIS_LENGTH/2,  AXIS_LENGTH);
        
        final Rotate rx = new Rotate(90, 0, 0, 0, Rotate.X_AXIS);
        final Rotate ry = new Rotate(90, 0, 0, 0, Rotate.Y_AXIS);
        final Rotate rz = new Rotate(90, 0, 0, 0, Rotate.Z_AXIS);
    

        final Cylinder xAxis = new Cylinder(1,AXIS_LENGTH+10);
        final Cylinder yAxis = new Cylinder(1,AXIS_LENGTH+10);
        final Cylinder zAxis = new Cylinder(1,AXIS_LENGTH+10);
        
        final Cylinder x2Axis = new Cylinder(1,AXIS_LENGTH);
        final Cylinder y2Axis = new Cylinder(1,AXIS_LENGTH);
        final Cylinder z2Axis = new Cylinder(1,AXIS_LENGTH);
        
        final Cylinder x2Axisb = new Cylinder(1,AXIS_LENGTH);
        final Cylinder y2Axisb = new Cylinder(1,AXIS_LENGTH);
        final Cylinder z2Axisb = new Cylinder(1,AXIS_LENGTH);
        
        final Cylinder y2Axisc = new Cylinder(1,AXIS_LENGTH);
        
        xAxis.getTransforms().addAll(tz,rz);
        yAxis.getTransforms().addAll(ty,ry);
        zAxis.getTransforms().addAll(tx,rx);
        
        x2Axis.getTransforms().addAll(tz2,rz);
        y2Axis.getTransforms().addAll(ty2,ry);
        z2Axis.getTransforms().addAll(tx2,rx);
        
        x2Axisb.getTransforms().addAll(tz3,rz);
        y2Axisb.getTransforms().addAll(ty3,ry);
        z2Axisb.getTransforms().addAll(tx3,rx);
        
        y2Axisc.getTransforms().addAll(tx3c,ry);
        
        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
        
        x2Axis.setMaterial(gridMaterial);
        y2Axis.setMaterial(gridMaterial);
        z2Axis.setMaterial(gridMaterial);
        x2Axisb.setMaterial(gridMaterial);
        y2Axisb.setMaterial(gridMaterial);
        z2Axisb.setMaterial(gridMaterial);
        
        y2Axisc.setMaterial(gridMaterial);
        y2Axisc.setVisible(false);
 
        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axismGroup.getChildren().addAll(x2Axis, y2Axis, z2Axis);
        axismGroup.getChildren().addAll(x2Axisb, y2Axisb, z2Axisb, y2Axisc);

        zm = y2Axis;
        zm2 = y2Axisb;
        zm3 = y2Axisc;
        
        xm = x2Axisb; 
        ym = z2Axisb;
        
        axisGroup.setVisible(true);
        axismGroup.setVisible(true);
        world.getChildren().addAll(axisGroup, axismGroup);
        
        for (int ax=0; ax<3; ax++)
            world.getChildren().addAll(ticsGroups[ax]);        
        for (int ax=0; ax<8; ax++)
            world.getChildren().add(ticMarksGroups[ax]);
        for (int ax=0; ax<gridLinesGroups.length; ax++){
            world.getChildren().add(gridLinesGroups[ax]);
            gridLinesGroups[ax].setVisible(false);
        }
        for (int ax=0; ax<gridSurfGroups.length; ax++){
            world.getChildren().add(gridSurfGroups[ax]);
            gridSurfGroups[ax].setVisible(false);
        }
                
        fog.setTranslateX(0.5*AXIS_LENGTH);
        fog.setTranslateY(0.5*AXIS_LENGTH);
        fog.setTranslateZ(0.5*AXIS_LENGTH);
            
        fog.getTransforms().addAll(label_heading, label_roll);
        fog.setVisible(false);
        for (int i=40; i>=0; i--){Rectangle grid_surf = new Rectangle(4*AXIS_LENGTH,4*AXIS_LENGTH);
            grid_surf.setFill(Color.WHITE);
            grid_surf.setTranslateX(-2*AXIS_LENGTH);
            grid_surf.setTranslateY(-2*AXIS_LENGTH);
            grid_surf.setTranslateZ(-AXIS_LENGTH*Math.sqrt(2)/2+i/40.0*AXIS_LENGTH*Math.sqrt(2));
            grid_surf.setOpacity(0);
            fog.getChildren().add(grid_surf);
        }
        world.getChildren().add(fog);    
    }   
    
    /**
     * Probably the most important (representative) function, as it translates 
     * the parsed data points into 3D elements.
     */
    public void plot() {
        draw=true;
        
        points.getChildren().clear();
        for (int ax=0; ax<point_shadows.length; ax++){
            point_shadows[ax].getChildren().clear();
            point_shadow_projections[ax].getChildren().clear();
        }
        
        int count = 0;        
        double[][] array = new double[dimensions][child.dataset.size()];
                
        int[] indexes = child.variable_selection();
        boolean[] logscales = child.logscale_selection();

        for (int i=0; i<dimensions; i+=1) System.out.print(logscales[i]+" ");System.out.println();
        
        // For every value series in the dataset (e.g. for different columns in .csv files)
        for (double[] values: child.dataset){
            
            // For every available dimension type (spatial etc.)
            for (int i=0; i<dimensions; i+=1)
                
                if (indexes[i]==-1)
                    array[i][count] = 0; // ignore if dimension is not used
                else {
                    int stringcard = child.stringtypes.get(indexes[i]).size();
                    if ((logscales[i]==false)||(stringcard>1 && stringcard<150))
                        array[i][count] = values[indexes[i]]; // copy as is
                    else
                        array[i][count] = Math.log(values[indexes[i]]); // apply logscale if used
                }
            count++;
        }
        if (count==0)
            return;
        count = 0;
        
        // For every dimension
        for (int i=0; i<dimensions-1; i++){
                        
            double max = Double.MIN_VALUE;
            double min = Double.MAX_VALUE;
            
            if (i!=6){ // not for z4 labels
                for (int j=0; j<array[i].length; j++){
                    if (array[i][j]>max) max=array[i][j];
                    if (array[i][j]<min) min=array[i][j];                
                }
                for (int j=0; j<array[i].length; j++){
                    array[i][j] = (array[i][j]-min)/(max-min);            
                }
            }
                       
            // Tics (0, 1, 2 are the spatial dimensions)
            if (i<3){
                int ax=i; if (i!=0) ax=3-i; // (applied after y <-> z renaming issue)
                
                // Erase previous tics
                for (Object t_:tics[ax].keySet()){
                    double t= (double) t_;
                    Group g= (Group) ((Pair)tics[ax].get(t)).getValue();
                    g.getChildren().clear();
                    g.getTransforms().clear();
                }
                tics[ax].clear(); // (double check if enough for garbage collection)      
                                
                // New tics
                double minor_tic = Math.pow(10, Math.floor(Math.log10(Math.abs(max-min))-1));
                double major_tic = 10 * minor_tic;
                
                System.out.println(min+" "+max+" "+minor_tic+" "+ major_tic+" "+ major_tic*Math.floor(min/major_tic));
                
                int stringcard = child.stringtypes.get(indexes[i]).size();                
                if (stringcard>1 && stringcard<150){
                    // Label ticks
                    for (String s: child.stringtypes.get(indexes[i]).keySet()){
                        tics[ax].put(child.stringtypes.get(indexes[i]).get(s)/(stringcard-1.0), new Pair(s, new Group()));
                    }                    
                } else {                    
                    // Numerical ticks
                    NumberFormat df = DecimalFormat.getInstance();
                    df.setMinimumFractionDigits(0);
                    df.setMaximumFractionDigits((int) (-Math.min(0,Math.log(minor_tic))));
                    df.setRoundingMode(RoundingMode.HALF_EVEN);
                    
                    for (double tic = major_tic*Math.floor(min/major_tic); tic<=max; tic+= major_tic){
                        double pos = (tic-min)/(max-min);
                        if (!(pos>=0 && pos<=1))
                            continue;
                        if (logscales[i]){  
                            tics[ax].put(pos, new Pair("E"+df.format(tic), new Group()));
                        }
                        else 
                            tics[ax].put(pos, new Pair(df.format(tic), new Group()));
                    }
                }                
            }            
        }
        drawTics();
        
        { // Prepare z4 labels (labels per point)
            point_labels.getChildren().clear();
            if ((indexes[6]!=-1) && (child.z4cb1.getSelectedIndex()!=2)){
                List <Pair<Double,Integer>> selection = new ArrayList <Pair<Double,Integer>>();
                for (int i=0; i<child.dataset.size(); i++)
                    selection.add(new Pair (child.dataset.get(i)[child.z4cb2.getSelectedIndex()],i));
                
                // Do a top/bottom N selection for decluttering, according to the GUI settings
                selection.sort(pairComparator);                                
                switch ((int) child.z4cb1.getSelectedIndex()){
                    case 0: // Top
                        for (int i=array[6].length-1-(int)child.z4js.getValue(); i>=0; i--){
                            array[6][selection.get(i).getValue()]=Float.NaN;
                        }
                        break;
                    case 1: // Bottom                        
                        for (int i=(int)child.z4js.getValue(); i<array[6].length; i++){
                            array[6][selection.get(i).getValue()]=Float.NaN;
                        }
                        break;
                    default: // All
                        break;
                }               
            }
        }
        
        double tmpsize = pointsize;
        double transperency = 0.7;
        double r=0,g=0.8,b=0;
        
        // For every point in the dataset,
        for (int i=0; i<child.dataset.size(); i++){
            PhongMaterial m = new PhongMaterial();         
            if (indexes[5]!=-1) // z3 transparency
                transperency =0.1+0.9*array[5][i];
            if (indexes[3]!=-1){ // z1 colour coding
                double x = array[3][i];
                // Viridis colour palette 
                r= x*x*x*-1215.35 + x*x*2144.48 + x*-733.556 + 60.4242; 
		g= x*x*x* 548.202 + x*x*-960.762+ x* 620.136 + 38.2424;
		b= x*x*x* 837.818 + x*x*-1588.92 + x*625.281 + 146.576;
		r=Math.floor(Math.min(255,Math.max(0,r)))/255;
		g=Math.floor(Math.min(255,Math.max(0,g)))/255;
		b=Math.floor(Math.min(255,Math.max(0,b)))/255;
            }
            
            // Prepare colours
            m.setDiffuseColor(Color.color(r,g,b,transperency));            
            Color shadow_color = Color.color(r,g,b,indexes[5]!=-1?transperency:shadow_opacity);
            
            PhongMaterial shadow_vector_material = new PhongMaterial(); 
            shadow_vector_material.setDiffuseColor(shadow_color);
            
            if (indexes[4]!=-1)
                tmpsize = pointsize*(0.1+0.9*array[4][i]);
            
            // According to the point type (sphere, box, bar), create the corresponding objects
            switch (pointtype){
                case 0: // Sphere
                    {
                    Sphere ob = new Sphere(tmpsize); // Point
                
                    ob.setMaterial(m);
                    ob.setTranslateX(array[0][i]*AXIS_LENGTH);
                    ob.setTranslateZ(array[1][i]*AXIS_LENGTH);
                    ob.setTranslateY(array[2][i]*AXIS_LENGTH);
                    points.getChildren().add(ob);                      
                    
                    // Shadows and projections
                    {
                        Cylinder sob = new Cylinder(tmpsize, array[2][i]*(AXIS_LENGTH));  sob.setMaterial(shadowMaterial);                  
                        sob.setTranslateX(array[0][i]*AXIS_LENGTH);
                        sob.setTranslateZ(array[1][i]*AXIS_LENGTH);
                        sob.setTranslateY((array[2][i]*(AXIS_LENGTH))/2);
                        point_shadows[0].getChildren().add(sob);       
                        
                        Cylinder rc = new Cylinder(tmpsize,0); rc.setMaterial(shadow_vector_material); 
                        rc.setTranslateX( array[0][i]*AXIS_LENGTH);
                        rc.setTranslateZ( array[1][i]*AXIS_LENGTH);
                        point_shadow_projections[0].getChildren().add(rc);
                    }
                    {
                        Cylinder sob = new Cylinder(tmpsize, array[0][i]*(AXIS_LENGTH)); sob.setMaterial(shadowMaterial);                   
                        sob.setTranslateX((array[0][i]*(AXIS_LENGTH))/2);
                        sob.setTranslateZ(array[1][i]*AXIS_LENGTH);
                        sob.setTranslateY(array[2][i]*AXIS_LENGTH);
                        sob.setRotate(-90);
                        point_shadows[1].getChildren().add(sob);
                        
                        Cylinder rc = new Cylinder(tmpsize,0); rc.setMaterial(shadow_vector_material);
                        rc.setTranslateZ( array[1][i]*AXIS_LENGTH);
                        rc.setTranslateY(array[2][i]*AXIS_LENGTH);
                        rc.setRotate(-90);
                        point_shadow_projections[1].getChildren().add(rc);
                    }
                    {
                        Cylinder sob = new Cylinder(tmpsize, array[1][i]*(AXIS_LENGTH)); sob.setMaterial(shadowMaterial);  
                        sob.setRotationAxis(new Point3D(1,0,0));
                        sob.setRotate(90);
                        sob.setTranslateX(array[0][i]*AXIS_LENGTH);
                        sob.setTranslateZ(array[1][i]*AXIS_LENGTH/2);
                        sob.setTranslateY(array[2][i]*AXIS_LENGTH);
                        point_shadows[2].getChildren().add(sob);
                        
                        Cylinder rc = new Cylinder(tmpsize,0); rc.setMaterial(shadow_vector_material); 
                        rc.setRotationAxis(new Point3D(1,0,0));
                        rc.setRotate(90);
                        rc.setTranslateX(array[0][i]*AXIS_LENGTH);
                        rc.setTranslateY(array[2][i]*AXIS_LENGTH);
                        point_shadow_projections[2].getChildren().add(rc);
                    }
                    {
                        Cylinder sob = new Cylinder(tmpsize, (1-array[0][i])*(AXIS_LENGTH)); sob.setMaterial(shadowMaterial);                   
                        sob.setTranslateX(((1+array[0][i])*(AXIS_LENGTH))/2);
                        sob.setTranslateZ(array[1][i]*AXIS_LENGTH);
                        sob.setTranslateY(array[2][i]*AXIS_LENGTH);
                        sob.setRotate(-90);
                        point_shadows[3].getChildren().add(sob);
                        
                        Cylinder rc = new Cylinder(tmpsize,0); rc.setMaterial(shadow_vector_material); 
                        rc.setRotate(-90);
                        rc.setTranslateZ(array[1][i]*AXIS_LENGTH);
                        rc.setTranslateY(array[2][i]*AXIS_LENGTH);
                        rc.setTranslateX(AXIS_LENGTH);
                        point_shadow_projections[3].getChildren().add(rc);
                    }
                    {
                        Cylinder sob = new Cylinder(tmpsize, (1-array[1][i])*(AXIS_LENGTH)); sob.setMaterial(shadowMaterial);  
                        sob.setRotationAxis(new Point3D(1,0,0));
                        sob.setRotate(90);
                        sob.setTranslateX(array[0][i]*AXIS_LENGTH);
                        sob.setTranslateZ((1+array[1][i])*AXIS_LENGTH/2);
                        sob.setTranslateY(array[2][i]*AXIS_LENGTH);
                        point_shadows[4].getChildren().add(sob);
                        
                        Cylinder rc = new Cylinder(tmpsize,0); rc.setMaterial(shadow_vector_material); 
                        rc.setRotationAxis(new Point3D(1,0,0));
                        rc.setRotate(90);
                        rc.setTranslateX(array[0][i]*AXIS_LENGTH);
                        rc.setTranslateY(array[2][i]*AXIS_LENGTH);
                        rc.setTranslateZ(AXIS_LENGTH);
                        point_shadow_projections[4].getChildren().add(rc);
                    }
                    
                    }
                    break;
                case 1: // Box
                    {
                    Box ob = new Box(tmpsize,tmpsize,tmpsize); // Point         
                    ob.setMaterial(m);
                    ob.setTranslateX(array[0][i]*AXIS_LENGTH);
                    ob.setTranslateZ(array[1][i]*AXIS_LENGTH);
                    ob.setTranslateY(array[2][i]*AXIS_LENGTH);
                    points.getChildren().add(ob); 
                    
                    // Shadows and projections
                    {
                        Box sob = new Box(tmpsize, array[2][i]*(AXIS_LENGTH)-tmpsize/2 ,tmpsize);  sob.setMaterial(shadowMaterial);                  
                        sob.setTranslateX(array[0][i]*AXIS_LENGTH);
                        sob.setTranslateZ(array[1][i]*AXIS_LENGTH);
                        sob.setTranslateY((array[2][i]*(AXIS_LENGTH)-tmpsize/2)/2);                        
                        point_shadows[0].getChildren().add(sob);
                        
                        Box rc = new Box(tmpsize, 0 ,tmpsize);  rc.setMaterial(shadow_vector_material);                  
                        rc.setTranslateX(array[0][i]*AXIS_LENGTH);
                        rc.setTranslateZ(array[1][i]*AXIS_LENGTH);
                        rc.setTranslateY(0);                        
                        point_shadow_projections[0].getChildren().add(rc);
                        
                    }                     
                    {
                        Box sob = new Box(array[0][i]*(AXIS_LENGTH)-tmpsize/2, tmpsize ,tmpsize); sob.setMaterial(shadowMaterial);                   
                        sob.setTranslateX((array[0][i]*(AXIS_LENGTH)-tmpsize/2)/2);
                        sob.setTranslateZ(array[1][i]*AXIS_LENGTH);
                        sob.setTranslateY(array[2][i]*AXIS_LENGTH);
                        point_shadows[1].getChildren().add(sob);
                        
                        Box rc = new Box(0, tmpsize ,tmpsize); rc.setMaterial(shadow_vector_material);                   
                        rc.setTranslateX(0);
                        rc.setTranslateZ(array[1][i]*AXIS_LENGTH);
                        rc.setTranslateY(array[2][i]*AXIS_LENGTH);
                        point_shadow_projections[1].getChildren().add(rc);
                                                
                    } 
                    {
                        Box sob = new Box(tmpsize, tmpsize, array[1][i]*(AXIS_LENGTH)-tmpsize/2); sob.setMaterial(shadowMaterial);                   
                        sob.setTranslateX(array[0][i]*AXIS_LENGTH);
                        sob.setTranslateZ((array[1][i]*(AXIS_LENGTH)-tmpsize/2)/2);
                        sob.setTranslateY(array[2][i]*AXIS_LENGTH);
                        point_shadows[2].getChildren().add(sob);
                        
                        Box rc = new Box(tmpsize, tmpsize, 0); rc.setMaterial(shadow_vector_material);                   
                        rc.setTranslateX(array[0][i]*AXIS_LENGTH);
                        rc.setTranslateZ(0);
                        rc.setTranslateY(array[2][i]*AXIS_LENGTH);
                        point_shadow_projections[2].getChildren().add(rc);
                                               
                    }                     
                    
                    {
                        Box sob = new Box(AXIS_LENGTH-array[0][i]*(AXIS_LENGTH)-tmpsize/2, tmpsize ,tmpsize); sob.setMaterial(shadowMaterial);                   
                        sob.setTranslateX(array[0][i]*(AXIS_LENGTH)+(AXIS_LENGTH-array[0][i]*(AXIS_LENGTH)+tmpsize/2)/2);
                        sob.setTranslateZ(array[1][i]*AXIS_LENGTH);
                        sob.setTranslateY(array[2][i]*AXIS_LENGTH);
                        point_shadows[3].getChildren().add(sob);
                        
                        Box rc = new Box(0, tmpsize ,tmpsize); rc.setMaterial(shadow_vector_material);                   
                        rc.setTranslateX(AXIS_LENGTH);
                        rc.setTranslateZ(array[1][i]*AXIS_LENGTH);
                        rc.setTranslateY(array[2][i]*AXIS_LENGTH);
                        point_shadow_projections[3].getChildren().add(rc);
                                                
                    } 
                    {
                        Box sob = new Box(tmpsize, tmpsize, AXIS_LENGTH-array[1][i]*(AXIS_LENGTH)-tmpsize/2); sob.setMaterial(shadowMaterial);                   
                        sob.setTranslateX(array[0][i]*AXIS_LENGTH);
                        sob.setTranslateZ(array[1][i]*(AXIS_LENGTH)+(AXIS_LENGTH-array[1][i]*(AXIS_LENGTH)+tmpsize/2)/2);
                        sob.setTranslateY(array[2][i]*AXIS_LENGTH);
                        point_shadows[4].getChildren().add(sob);
                        
                        Box rc = new Box(tmpsize, tmpsize, 0); rc.setMaterial(shadow_vector_material);                   
                        rc.setTranslateX(array[0][i]*AXIS_LENGTH);
                        rc.setTranslateZ(AXIS_LENGTH);
                        rc.setTranslateY(array[2][i]*AXIS_LENGTH);
                        point_shadow_projections[4].getChildren().add(rc);
                        
                    } 
                    }
                    
                    break;
                case 2: // Bar
                    {
                    Box ob = new Box(tmpsize,array[2][i]*AXIS_LENGTH,tmpsize); // Point (no projections for bars)
                    ob.setMaterial(m);
                    ob.setTranslateX(array[0][i]*AXIS_LENGTH);
                    ob.setTranslateZ(array[1][i]*AXIS_LENGTH);
                    ob.setTranslateY(array[2][i]*AXIS_LENGTH/2);
                    points.getChildren().add(ob);
                    }
                    break;
            }
            
            if ((indexes[6]!=-1) && (!Double.isNaN(array[6][i]))) {
                // Label text obect using a FXyz mesh
                Text3DMesh tm = new Text3DMesh(child.datasetStringed.get(i)[indexes[6]].toString(), "Liberation Serif", 25, true); 
                System.out.println(i+" "+array[6][i]);
                tm.setHeight(0);
                tm.setTextureModeNone(Color.BLACK);
                tm.setScaleX(0.8*(int)child.z4cb3.getValue()/12.0);  tm.setScaleY(0.8*(int)child.z4cb3.getValue()/12.0);
                tm.setTranslateX(-tm.getLayoutBounds().getCenterX());
                tm.setTranslateY(-tm.getLayoutBounds().getCenterY());                
                
                // Enclose in an invisible border (for now)
                Rectangle re = new Rectangle();
                re.setFill(Color.WHITESMOKE);
                re.setWidth(tm.getLayoutBounds().getWidth());
                re.setHeight(tm.getLayoutBounds().getHeight());  
                re.setTranslateX(-tm.getLayoutBounds().getWidth()/2);
                re.setTranslateY(-tm.getLayoutBounds().getHeight()/2);
                re.setTranslateZ(3); // workaround for overlapping surfaces
                re.setScaleX((int)child.z4cb3.getValue()/12.0);  re.setScaleY((int)child.z4cb3.getValue()/12.0);
                re.setVisible(false);
                
                // Create a group for transformations
                Group tgr = new Group();                
                tgr.getChildren().addAll(tm, re);                
                tgr.getTransforms().addAll(label_heading, label_roll);
                tgr.setTranslateX(array[0][i]*AXIS_LENGTH);
                tgr.setTranslateZ(array[1][i]*(AXIS_LENGTH));
                double tmpsize_ = tmpsize/2;
                if (pointtype==0) // Sphere
                    tmpsize_ = tmpsize;
                tgr.setTranslateY(array[2][i]*AXIS_LENGTH+tmpsize_+tm.getLayoutBounds().getHeight()/2+2);
                point_labels.getChildren().add(tgr);
            }
            count += 1;
            if (count==50000) break; // Impose a hard limit for the number of data points for now
        }
        System.out.println(child.dataset.size());
        plotw.getChildren().clear();
        plotw.getChildren().add(points);
        
        // Place grids to the front to allow transparency
        for (int ax=0; ax<gridLinesGroups.length; ax++){
            world.getChildren().remove(gridLinesGroups[ax]);
            world.getChildren().add(gridLinesGroups[ax]);
        }         
        for (int ax=0; ax<point_shadow_projections.length; ax++){
            world.getChildren().remove(point_shadow_projections[ax]);
            world.getChildren().add(point_shadow_projections[ax]);
        }
        for (int ax=0; ax<point_shadows.length; ax++){
            world.getChildren().remove(point_shadows[ax]);
            world.getChildren().add(point_shadows[ax]);            
        }
        for (int ax=0; ax<gridSurfGroups.length; ax++){
            world.getChildren().remove(gridSurfGroups[ax]);
            world.getChildren().add(gridSurfGroups[ax]);           
        }   
        
        world.getChildren().remove(fog);
        world.getChildren().add(fog);
        
        // (seelabels already does that)
        if (depth_colormap) {
            depth_color();
        }                
        draw=false; 
    }
    
    /**
     * Comparator implementation that sorts the visual elements based on their
     * distance as a workaround for JavaFX's inability to show transparency 
     * correctly for objects created later. 
     * see: https://stackoverflow.com/questions/29308397/javafx-3d-transparency
     */
    public class depth_tester implements Comparator<Node> {
        
        public static double get_distance_from_cam (Node n, Xform camera){            
            return Math.sqrt(get_distance_from_cam_no_sqrt(n, camera));
        }
        
        public static double get_distance_from_cam_no_sqrt (Node n, Xform camera){
            double distance_from_cam = Math.pow(n.getLocalToSceneTransform().getTx() - camera.getBoundsInParent().getCenterX(),2);
            distance_from_cam += Math.pow(n.getLocalToSceneTransform().getTz()- camera.getBoundsInParent().getCenterZ(),2);
            distance_from_cam += Math.pow(n.getLocalToSceneTransform().getTy()- camera.getBoundsInParent().getCenterY(),2);
            return distance_from_cam;
        }
        
        public int compare(Node a, Node b)
        {            
            double distance_from_cam_a = get_distance_from_cam_no_sqrt(a, cameraXform);
            double distance_from_cam_b = get_distance_from_cam_no_sqrt(b, cameraXform);
            return (distance_from_cam_a>distance_from_cam_b?-1:1);
        }
    }

    Comparator cmpr = new depth_tester();
    
    /**
     * Function that uses the comparator for the transparency workaround. It first
     * sorts the elements inside a list by pointers. Then it removes them temporarily 
     * from their group and inserts them back in order so that their order is adjusted.
     */    
    private void depth_sort(Xform[] l){
               
        List<Object> ar = new ArrayList<Object>();
        List<Integer> visible = new ArrayList<Integer>();
        for (int i=0; i<l.length; i++)
            if (l[i]!=null && l[i].isVisible()){                
                visible.add(i);
                ar.addAll(l[i].getChildren());
            }
        
        try{
            ar.sort(cmpr);
        }catch(Exception e){}
           
        for (Object o:ar){
            Node n = (Node)o;  
            
            for (int i:visible)
                {
                if (l[i].getChildren().contains(n)){
                    l[i].getChildren().remove(n);
                    l[i].getChildren().add(n);
                }
            }
        }        
    }    
    
    /**
     * Classical JavaFX code for handling mouse events to rotate the scene etc.
     * It also includes the infinite rotation effect when it is enables in the View
     * tab in the GUI.
     */
    private void handleMouse(Scene scene, final Node root) {
 
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();                                
            }
        });
        
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX); 
                mouseDeltaY = (mousePosY - mouseOldY);

               double modifier = 1.0;
               double modifierFactor = MOUSE_SPEED;
               
               if (me.isControlDown()) {
                    modifier = CONTROL_MULTIPLIER;
                } 
                if (me.isShiftDown()) {
                    modifier = SHIFT_MULTIPLIER;
                }     
                if (me.isPrimaryButtonDown()) {
                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() -
                       mouseDeltaX*modifierFactor*modifier*ROTATION_SPEED);  // 
                   cameraXform.rx.setAngle(cameraXform.rx.getAngle() +
                       mouseDeltaY*modifierFactor*modifier*ROTATION_SPEED);  // -
                   rotation_remainder=Math.max(0.00001,mouseDeltaX*modifierFactor*modifier*ROTATION_SPEED);
                   last_time = System.currentTimeMillis();
                   //System.out.println(cameraXform.rx.getAngle()+", "+cameraXform.ry.getAngle());
                }
                else if (me.isSecondaryButtonDown()) {
                    double z = camera.getTranslateZ();
                    double newZ = z + mouseDeltaX*MOUSE_SPEED*modifier;
                    camera.setTranslateZ(newZ);
                }
                else if (me.isMiddleButtonDown()) {
                   cameraXform2.t.setX(cameraXform2.t.getX() + 
                      mouseDeltaX*MOUSE_SPEED*modifier*TRACK_SPEED);  
                   cameraXform2.t.setY(cameraXform2.t.getY() + 
                      mouseDeltaY*MOUSE_SPEED*modifier*TRACK_SPEED);  
                }

            }
        }); // setOnMouseDragged
        
        scene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    CAMERA_DISTANCE += 20;
                } 
                if (event.getDeltaY() < 0){
                    CAMERA_DISTANCE -= 20;
                }
                camera.setTranslateZ(CAMERA_DISTANCE);
            }
        });
    } //handleMouse
    
    /**
     * Keyboard-based navigation. It is undocumented, but it is left as a placeholder
     * for future functionality.
     */
    private void handleKeyboard (Scene scene, final Node roo1) {

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
               switch (event.getCode()) {
                    case Z:
                       cameraXform2.t.setX(0.0);
                       cameraXform2.t.setY(CAMERA_INITIAL_HEIGHT);
                       cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                       cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
                       break;
                    case X:
                        axisGroup.setVisible(!axisGroup.isVisible());
                        axismGroup.setVisible(!axismGroup.isVisible());
                        break;
                    case V:                       
                       break;
               } // switch
            } // handle()
        });  // setOnKeyPressed
    }  //  handleKeyboard()
    
    /**
     * The main() method is ignored in correctly deployed JavaFX 
     * application. main() serves only as fallback in case the 
     * application can not be launched through deployment artifacts, 
     * e.g., in IDEs with limited FX support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);   
        System.exit(0);
    }
}
