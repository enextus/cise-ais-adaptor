package eu.cise.adaptor.tbs;

import eu.cise.adaptor.AISAdaptorConfig;
import org.aeonbits.owner.Config;

@SuppressWarnings("unused")
@Config.Sources({"file:${conf.dir}ais-adaptor.properties",
        "classpath:ais-adaptor.properties"})
public interface FileAISSourceConfig extends AISAdaptorConfig {

    @Key("ais-source.file.name")
    String getAISSourceFilename();

}
