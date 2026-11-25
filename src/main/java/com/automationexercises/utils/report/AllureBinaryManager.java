        package com.automationexercises.utils.report;

        import com.automationexercises.FileUtils;
        import com.automationexercises.utils.TerminalUtils;
        import com.automationexercises.utils.logs.LogsManager;
        import org.apache.commons.lang3.SystemUtils;
        import org.jsoup.Jsoup;

        import java.io.BufferedInputStream;
        import java.io.File;
        import java.io.OutputStream;
        import java.net.URI;
        import java.nio.file.Files;
        import java.nio.file.Path;
        import java.nio.file.Paths;
        import java.util.zip.ZipEntry;
        import java.util.zip.ZipInputStream;

        public class AllureBinaryManager {

            public static void downloadAndExtract() {
                try {
                    String version = LazyHolder.VERSION;
                    Path extractionDir = AllureConstant.EXTRACTION_DIR
                            .resolve("allure-" + version);
                    if (Files.exists(getExcutable())) {
                        LogsManager.info("Allure binary already exists");
                        return;
                    }

                    // Give execute permission to the directory
                    if (!FileUtils.OSUtils.getCurrentOS().equals(FileUtils.OSUtils.OS.WINDOWS)){
                        TerminalUtils.excuteTerminalCommand("chmod", "u+x " + AllureConstant.EXTRACTION_DIR.toString());
                    }

                    Path zipPath = downloadZip();
                    extractZip(zipPath);

                    LogsManager.info("Allure binary downloaded and extracted successfully ");


                    if (!FileUtils.OSUtils.getCurrentOS().equals(FileUtils.OSUtils.OS.WINDOWS)) {
                        TerminalUtils.excuteTerminalCommand("chmod", "u+x " + getExcutable().toString());
                    }
                } catch (Exception e) {
                    LogsManager.error("Failed in downloadAndExtract: " + e.getMessage());
                }
            }

            public static Path getExcutable() {
                String version = LazyHolder.VERSION;

                Path binaryPath = AllureConstant.EXTRACTION_DIR
                        .resolve("allure-" + version)
                        .resolve("bin")
                        .resolve("allure");

                return SystemUtils.IS_OS_WINDOWS
                        ? binaryPath.resolveSibling("allure.bat")
                        : binaryPath;
            }


            private static class LazyHolder {
                static final String VERSION = resolveVersion();

                private static String resolveVersion() {
                    try {
                        String url = Jsoup.connect("https://github.com/allure-framework/allure2/releases/latest")
                                .followRedirects(true).execute().url().toString();
                        return url.split("/tag/")[1];
                    } catch (Exception e) {
                        throw new IllegalStateException("Failed to resolve Allure version from GitHub releases: " + e.getMessage(), e);
                    }
                }
            }

            // download ZIP file for Allure
            private static Path downloadZip() {
                try {
                    String url = STR."\{AllureConstant.ALLURE_ZIP_BASE_URL}\{LazyHolder.VERSION}/allure-commandline-\{LazyHolder.VERSION}.zip";
                    Path zipFile = Paths.get(AllureConstant.EXTRACTION_DIR.toString(), "Alure-" + LazyHolder.VERSION + ".zip");

                    if (!Files.exists(zipFile)) {
                        Files.createDirectories(AllureConstant.EXTRACTION_DIR);
                        try (BufferedInputStream in = new BufferedInputStream(new URI(url).toURL().openStream());
                             OutputStream out = Files.newOutputStream(zipFile)) {
                            in.transferTo(out);
                        } catch (Exception e) {
                            LogsManager.error("Invalid URL or failed to download Allure binary: " + e.getMessage());
                        }
                    }
                    return zipFile;
                } catch (Exception e) {
                    LogsManager.error("Failed to download Allure binary: " + e.getMessage());
                    return Paths.get("");
                }
            }

            // Extract ZIP file for Allure
            private static void extractZip(Path zipPath) {
                try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath))) {
                    ZipEntry entry;
                    while ((entry = zipInputStream.getNextEntry()) != null) {
                        Path filePath = Paths.get(AllureConstant.EXTRACTION_DIR.toString(), File.separator, entry.getName());
                        if (entry.isDirectory()) {
                            Files.createDirectories(filePath);
                        } else {
                            Files.createDirectories(filePath.getParent());
                            Files.copy(zipInputStream, filePath);
                        }
                    }
                } catch (Exception e) {
                    LogsManager.error("Failed to extract Allure binary: " + e.getMessage());
                }
            }
        }