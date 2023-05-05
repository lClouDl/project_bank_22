package com.bank.account.models;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RevInfoTest {

    public static List<RevInfo> revInfoList;

    public static List<RevInfo> getListRevInfo() {
        revInfoList = new ArrayList<>();
        revInfoList.add(getOneRevInfoWithParam(1, new Audit()));
        revInfoList.add(getOneRevInfoWithParam(2, new Audit()));
        return revInfoList;
    }

    public static RevInfo getOneRevInfoWithParam(int id, Audit audit) {
        return new RevInfo(id,
                id*10000L,
                "User" + id,
                audit);
    }

    @Test
    void noArgsConstructorTest () {
        RevInfo revInfo = new RevInfo();
        List<RevInfo> revInfoList = getListRevInfo();
        revInfoList.add(revInfo);
        assertEquals(revInfoList.size(), 3);
    }

    @Test
    void AllArgsConstructorTest () {
        RevInfo revInfo = getOneRevInfoWithParam(3, new Audit());
        List<RevInfo> revInfoList = getListRevInfo();
        revInfoList.add(revInfo);
        assertEquals(revInfoList.size(), 3);
    }

    @Test
    void lombokDataTest () {
        RevInfo revInfo = getOneRevInfoWithParam(4, new Audit());
        RevInfo revInfoForTest = new RevInfo();
        revInfoForTest.setId(revInfo.getId());
        revInfoForTest.setTimestamp(revInfo.getTimestamp());
        revInfoForTest.setUsername(revInfo.getUsername());
        revInfoForTest.setAudit(revInfo.getAudit());
        assertEquals(revInfo, revInfoForTest);
    }

}