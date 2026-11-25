package com.automationexercises.media;

import com.automation.remarks.video.RecorderFactory;
import com.automation.remarks.video.recorder.IVideoRecorder;
import com.automation.remarks.video.recorder.VideoRecorder;
import com.automationexercises.utils.dataReader.PropertyReader;
import ws.schild.jave.Encoder;
import com.automationexercises.utils.logs.LogsManager;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;

import java.io.File;

public class ScreenRecordManager {

    public final static String RECORDING_PATH = "/test-output/screen-records/";
    public static final ThreadLocal<IVideoRecorder> recorder = new ThreadLocal<>();

    /**
     * Start screen recording for the current thread.
     */
    public static void startRecording() {

        if (PropertyReader.getProperty("recordTests").equalsIgnoreCase("true")) {
            LogsManager.info("Screen recording is disabled. Skipping recording for thread: " + Thread.currentThread().getName());

        try {
            // Ensure the recording directory exists
            File recordingDir = new File(RECORDING_PATH);
            if (!recordingDir.exists()) {
                recordingDir.mkdirs();
            }

            if (PropertyReader.getProperty("executionType").equalsIgnoreCase("local")) {
                // Configure the recorder and use the custom recording directory and file name
                recorder.set(RecorderFactory.getRecorder(VideoRecorder.conf().recorderType()));

                // Start recording
                recorder.get().start();
                LogsManager.info("Screen recording started for thread: " + Thread.currentThread().getName());

            }


        } catch (Exception e) {
            LogsManager.error("Failed to start screen recording: " + e.getMessage());
        }
    }
    }
    /**
     * Stop screen recording for the current thread.
     */
    public static void stopRecording(String testMethodName) {
        try {
            if (recorder.get() != null) {
                String videoFilePath = String.valueOf(recorder.get().stopAndSave(testMethodName));
                File videoFile = new File(videoFilePath);

                // Log the file path for debugging purposes
                LogsManager.info("Video file saved at : " + videoFile.getAbsolutePath());

                // Convert the video to MP4 format
                File mp4File = encodeRecording(videoFile);
                LogsManager.info("Screen recording saved as MP4: " + mp4File.getName());
            }
        } catch (Exception e) {
            LogsManager.error("Failed to stop screen recording: " + e.getMessage());
        } finally {
            recorder.remove(); // Clean up the ThreadLocal variable
        }
    }

    /**
     * Converts recorded video file to MP4 format.
     */
    private static File encodeRecording(File sourceFile) {
        File targetFile = new File(sourceFile.getAbsolutePath().replace(".avi", ".mp4"));
        try {
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("aac");

            VideoAttributes video = new VideoAttributes();
            video.setCodec("libx264");

            EncodingAttributes encodingAttributes = new EncodingAttributes();
            encodingAttributes.setOutputFormat("mp4");
            encodingAttributes.setAudioAttributes(audio);
            encodingAttributes.setVideoAttributes(video);

            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(sourceFile), targetFile, encodingAttributes);

            // Delete the original AVI file after successful conversion
            if (sourceFile.exists()) {
                sourceFile.delete();
                LogsManager.info("Original AVI file deleted: " + sourceFile.getName());
            }

        } catch (Exception e) {
            LogsManager.error("Failed to convert video to MP4: " + e.getMessage());
        }

        return targetFile;
    }
}