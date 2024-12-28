package de.verdox.mccreativelab.impl.paper.plugin;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.bukkit.craftbukkit.util.Versioning;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.repository.RepositoryPolicy;

public class PaperPlatformDependencies {
    public static void useDependencies(PluginClasspathBuilder pluginClasspathBuilder) {
        String version = Versioning.getBukkitVersion();
        MavenLibraryResolver resolver = new MavenLibraryResolver();
        resolver.addRepository(
                new RemoteRepository.Builder("maven", "default", "https://repo.verdox.de/snapshots")
                        .setSnapshotPolicy(new RepositoryPolicy(true, RepositoryPolicy.UPDATE_POLICY_ALWAYS, RepositoryPolicy.CHECKSUM_POLICY_WARN))
                        .build()
        );
        resolver.addDependency(new Dependency(new DefaultArtifact("de.verdox:vserializer:1.0.5-SNAPSHOT"), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("de.verdox.mccreativelab.mcc-wrapper:api:" + version), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("de.verdox.mccreativelab.mcc-wrapper", "vanilla", "dev", "jar", version), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("de.verdox.mccreativelab.mcc-wrapper", "paper", "dev", "jar", version), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("de.verdox.mccreativelab", "mcc-util", "dev", "jar", version), null));
        resolver.addDependency(new Dependency(new DefaultArtifact("de.verdox.mccreativelab:mcc-pack-generator:" + version), null));

        pluginClasspathBuilder.addLibrary(resolver);
    }
}
