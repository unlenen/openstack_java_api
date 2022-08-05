package unlenen.cloud.openstack.be.constant;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtil {

  private FileUtil() {
    // restrict instantiation
  }

  public static final String folderPath =  "/home/argela/uploadFiles/";
  public static final Path filePath = Paths.get(folderPath);

}
