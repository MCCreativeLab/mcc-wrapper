package de.verdox.mccreativelab.impl.paper.plugin;

import de.verdox.mccreativelab.impl.paper.platform.PaperPlatform;
import de.verdox.mccreativelab.wrapper.platform.MCCPlatform;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import net.kyori.adventure.text.Component;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;

public class MCCPaperPluginLoader implements PluginBootstrap, PluginLoader {
    @Override
    public void bootstrap(BootstrapContext context) {
        context.getLogger().info("Initializing mcc-platform " + MCCPlatform.INSTANCE);
        MCCPlatform.INSTANCE.setup(createPlatform(), MCCPlatform::init);
        MCCPlatform.getInstance().triggerLifecycleEvent(MCCPlatform.Lifecycle.BOOTSTRAP);
    }

    @Override
    public void classloader(PluginClasspathBuilder classpathBuilder) {
        String version = classpathBuilder.getContext().getConfiguration().getVersion();
        classpathBuilder.getContext().getLogger().info(Component.text("Starting MCC Paper Platform for version: " + version));

        MavenLibraryResolver resolver = new MavenLibraryResolver();
        resolver.addRepository((new RemoteRepository.Builder("verdox-repo", "default", "https://repo.verdox.de/snapshots")).build());
        resolver.addDependency(new Dependency(new DefaultArtifact("de.verdox:vserializer:1.1-SNAPSHOT"), (String) null));

        MavenLibraryResolver central = new MavenLibraryResolver();
        central.addRepository(new RemoteRepository.Builder("maven-central", "default", "https://repo.maven.apache.org/maven2/").build());
        central.addDependency(new Dependency(new DefaultArtifact("io.vertx:vertx-core:4.5.10"), (String) null));
        central.addDependency(new Dependency(new DefaultArtifact("com.hierynomus:sshj:0.38.0"), (String) null));
        central.addDependency(new Dependency(new DefaultArtifact("org.tukaani:xz:1.9"), (String) null));
        central.addDependency(new Dependency(new DefaultArtifact("commons-io:commons-io:2.17.0"), (String) null));
        central.addDependency(new Dependency(new DefaultArtifact("ws.schild:jave-all-deps:3.5.0"), (String) null));
        central.addDependency(new Dependency(new DefaultArtifact("net.bytebuddy:byte-buddy:1.15.10"), (String) null));
        central.addDependency(new Dependency(new DefaultArtifact("org.apache.commons:commons-compress:1.27.1"), (String) null));
        central.addDependency(new Dependency(new DefaultArtifact("com.google.guava:guava:33.3.1-jre"), (String) null));
        central.addDependency(new Dependency(new DefaultArtifact("org.apache.commons:commons-lang3:3.17.0"), (String) null));
        //resolver.addDependency(new Dependency(new DefaultArtifact("de.verdox.mccreativelab:mcc-util:1.21.4-R0.1-SNAPSHOT"), (String)null));
        //resolver.addDependency(new Dependency(new DefaultArtifact("de.verdox.mccreativelab:mcc-pack-generator:1.21.4-R0.1-SNAPSHOT"), (String)null));
        classpathBuilder.addLibrary(resolver);
        classpathBuilder.addLibrary(central);
    }

    public MCCPlatform createPlatform() {
        return new PaperPlatform();
    }
}
