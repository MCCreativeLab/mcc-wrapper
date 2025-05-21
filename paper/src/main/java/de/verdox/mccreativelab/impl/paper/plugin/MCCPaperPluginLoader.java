package de.verdox.mccreativelab.impl.paper.plugin;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import net.kyori.adventure.text.Component;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MCCPaperPluginLoader implements PluginLoader {

    @Override
    public void classloader(PluginClasspathBuilder classpathBuilder) {
        String version = readVersion();

        classpathBuilder.getContext().getLogger().info(Component.text("Downloading dependencies for MCC Paper Platform " + version));

        MavenLibraryResolver resolver = new MavenLibraryResolver();
        resolver.addRepository((new RemoteRepository.Builder("verdox-repo", "default", "https://repo.verdox.de/snapshots")).build());
        resolver.addRepository(new RemoteRepository.Builder("maven-central", "default", "https://repo.maven.apache.org/maven2/").build());

        resolver.addDependency(new Dependency(new DefaultArtifact("de.verdox.mccreativelab.mcc-wrapper", "vanilla", "dev", "jar", version), (String) null));
        classpathBuilder.addLibrary(resolver);
    }

    private static String readVersion() {
        try (InputStream in = MCCPaperPluginLoader.class.getClassLoader().getResourceAsStream("mcc-version.txt")) {
            if (in == null) {
                throw new IOException("Could not find mcc-version.txt");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                return reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not read version file", e);
        }
    }
}
