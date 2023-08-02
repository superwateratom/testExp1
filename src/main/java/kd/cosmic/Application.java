package kd.cosmic;

import kd.cosmic.server.Launcher;

/**
 * 启动本地应用程序(微服务节点)
 */
public class Application {

    public static void main(String[] args) {
        Launcher cosmic = new Launcher();

        cosmic.setClusterNumber("cosmic");
        cosmic.setTenantNumber("ierp");
        cosmic.setServerIP("127.0.0.1");

        cosmic.setAppName("cosmic-zhanghaolin-Ka5rRWcH");
        cosmic.setWebPath("C:/Users/zhanghaolin/IdeaProjects/软件杯-server/webapp/static-file-service");


        cosmic.setStartWithQing(false);

        cosmic.start();
    }
}