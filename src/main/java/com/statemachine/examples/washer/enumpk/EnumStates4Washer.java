package com.statemachine.examples.washer.enumpk;

/**
 * Created by zn.wang on 17/4/26.
 */
public enum  EnumStates4Washer {
    RUNNING,
    HISTORY,
    END,
    WASHING,//洗涤中
    RINSING,//冲洗中
    DRYING,//甩干中
    POWEROFF
}
