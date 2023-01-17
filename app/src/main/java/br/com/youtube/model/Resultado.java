package br.com.youtube.model;

import java.util.List;

public class Resultado {

    //https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&maxResults=20&key=AIzaSyDcYzasP83qXxPNvKgjZG2xiEOYJA8zXbI&channelId=UCVHFbqXqoYvEWM1Ddxl0QDg

    public String regionCode;
    public PageInfo pageInfo;
    public List<Items> items;
}
