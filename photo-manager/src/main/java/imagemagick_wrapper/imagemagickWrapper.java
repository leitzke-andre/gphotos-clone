package imagemagick_wrapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class imagemagickWrapper {
    public static boolean createThumbnail(Path source, Path destination) {
        //convert -resize 300x 32.jpg 32_thumb.png
        //convert -resize 300x <source> <destination>
        try {
            List<String> cmd = List.of("convert", "-resize", "300x", source.toString(), destination.toString());
            ProcessBuilder builder = new ProcessBuilder(cmd);

            //bring process output to application out
            builder.inheritIO();
            Process process = builder.start();
            boolean finished = process.waitFor(3, TimeUnit.SECONDS);

            if (!finished) {
                process.destroy();
            }

            return finished;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
