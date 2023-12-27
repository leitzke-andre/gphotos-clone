import imagemagick_wrapper.imagemagickWrapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class App {

    static String userHome = System.getProperty("user.home");
    static Path thumbnailsDirectory = Path.of(userHome).resolve("dev/assets/sample-images/thumbnails");

    public static void main(String[] args) throws IOException{
        Files.createDirectories(thumbnailsDirectory);
        Path sourceDirectory = Path.of("/home/andre/dev/assets/sample-images/100-100-color");

        try (Stream<Path> files = Files.walk(sourceDirectory)
                .filter(Files::isRegularFile)
                .filter(App::isImage)) {
            files.forEach(f -> {
                imagemagickWrapper.createThumbnail(f, thumbnailsDirectory.resolve(f.getFileName()));
            });
        }

        System.out.println("Finished");
    }

    private static boolean isImage(Path path) {
        try {
            String mimeType = Files.probeContentType(path);
            return mimeType != null && mimeType.contains("image");

        } catch (IOException e) {
            return false;
        }
    }
}
