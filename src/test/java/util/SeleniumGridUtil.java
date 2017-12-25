package util;

import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.utils.SelfRegisteringRemote;
import org.openqa.grid.internal.utils.configuration.GridHubConfiguration;
import org.openqa.grid.internal.utils.configuration.GridNodeConfiguration;
import org.openqa.grid.internal.utils.configuration.StandaloneConfiguration;
import org.openqa.grid.web.Hub;
import org.openqa.selenium.remote.server.SeleniumServer;

public class SeleniumGridUtil {

    public void startHub() throws Exception {
        Hub hub = new Hub(GridHubConfiguration.loadFromJSON("src\\test\\resources\\conf\\hubConfig.json"));
        hub.start();
    }

    public void startNode() throws Exception {
        RegistrationRequest request = new RegistrationRequest(GridNodeConfiguration.loadFromJSON("src\\test\\resources\\conf\\nodeConfig.json"));
        SeleniumServer seleniumServer = new SeleniumServer(StandaloneConfiguration.loadFromJSON("src\\test\\resources\\conf\\nodeConfig.json"));
        SelfRegisteringRemote remote = new SelfRegisteringRemote(request);
        remote.setRemoteServer(seleniumServer);
        remote.startRemoteServer();
        remote.startRegistrationProcess();
        System.out.println("grid started");
    }

    public static void main(String[] args) throws Exception {
        SeleniumGridUtil seleniumGridUtil = new SeleniumGridUtil();
        seleniumGridUtil.startHub();
        seleniumGridUtil.startNode();
    }
}
