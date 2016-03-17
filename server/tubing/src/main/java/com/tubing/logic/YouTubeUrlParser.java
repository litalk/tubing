package com.tubing.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeUrlParser {
    
    // (?:youtube(?:-nocookie)?\.com\/(?:[^\/\n\s]+\/\S+\/|(?:v|e(?:mbed)?)\/|\S*?[?&]v=)|youtu\.be\/)([a-zA-Z0-9_-]{11})
    final static String reg =
            "(?:youtube(?:-nocookie)?\\.com\\/(?:[^\\/\\n\\s]+\\/\\S+\\/|(?:v|e(?:mbed)?)\\/|\\S*?[?&]v=)|youtu\\.be\\/)([a-zA-Z0-9_-]{11})";
    
    public static String getVideoId(String videoUrl) {
        
        String ret = null;
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(videoUrl);
        if (matcher.find()) {
            ret = matcher.group(1);
        }
        
        return ret;
    }
}
