package com.unipi.msc.jobfinderapi.Shared;

public class Tools {
    public static boolean between(Long from, Long to, Long number){
        if (from==null && to!=null){
            return number <= to;
        } else if (from!=null && to==null) {
            return number >= from;
        }else if (from != null){
            return number >= from && number <= to;
        }
        return true;
    }
}
