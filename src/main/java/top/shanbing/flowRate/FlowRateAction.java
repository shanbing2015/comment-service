package top.shanbing.flowRate;

/**
 * @author shanbing
 * @date 2018/8/28.
 */
public enum FlowRateAction {
    /**api限流*/
    API_FLOWRATE,

    /**ip限流*/
    IP_FLOWRATE,

    /**api_ip限流*/
    APIIP_FLOWRATE,

    /**先api_ip限流，再api限流*/
    API_APIIP_FLOWRATE
}
