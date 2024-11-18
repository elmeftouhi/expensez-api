package com.elmeftouhi.expensez.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    public void util_checkDateIsValid_whenCorrectFormat(){
        String strDate = "2024-11-18";
        assert( Util.isValidDate(strDate) ).equals(true);
    }

    @Test
    public void util_checkDateIsValid_whenWrongFormat(){
        String strDate = "2024-11,18,2024";
        assert( Util.isValidDate(strDate) ).equals(true);
    }

}