package top.shanbing.common;

/**
 * Created by shanbing.top on 2018/7/27.
 */
public class UserAgent {
    public String browserType;//浏览器类型
    public String browserVersion;//浏览器版本
    public String platformType;//平台类型
    public String platformSeries;//平台系列
    public String platformVersion;//平台版本

    public UserAgent(){}

    public UserAgent(String browserType, String browserVersion,String platformType, String platformSeries, String platformVersion){
        this.browserType = browserType;
        this.browserVersion = browserVersion;
        this.platformType = platformType;
        this.platformSeries = platformSeries;
        this.platformVersion = platformVersion;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "browserType='" + browserType + '\'' +
                ", browserVersion='" + browserVersion + '\'' +
                ", platformType='" + platformType + '\'' +
                ", platformSeries='" + platformSeries + '\'' +
                ", platformVersion='" + platformVersion + '\'' +
                '}';
    }
}
