/* Copyright 2016 Michael Sladoje and Mike Schälchli. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package ch.zhaw.facerecognition.PreProcessor.BrightnessCorrection;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.facerecognition.Helpers.FileHelper;
import ch.zhaw.facerecognition.Helpers.MatName;
import ch.zhaw.facerecognition.PreProcessor.Command;
import ch.zhaw.facerecognition.PreProcessor.PreProcessor;

public class GammaCorrection implements Command {
    private double gamma = 0.2;
    private static final Scalar INT_MAX = new Scalar(255);

    public GammaCorrection(double gamma) {
        this.gamma = gamma;
    }

    public PreProcessor preprocessImage(PreProcessor preProcessor) {
        List<Mat> images = preProcessor.getImages();
        List<Mat> processed = new ArrayList<Mat>();
        for (Mat img : images){
            img.convertTo(img, CvType.CV_32F);
            Core.divide(img, INT_MAX, img);
            Core.pow(img, gamma, img);
            Core.multiply(img, INT_MAX, img);
            img.convertTo(img, CvType.CV_8U);
            processed.add(img);
        }
        preProcessor.setImages(processed);
        return preProcessor;
    }
}
