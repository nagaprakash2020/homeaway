package com.ndanda.homeaway.data;

import java.util.List;

public class events {

    private links[] links;
    private int id;
    private stats stats;
    private String title;
    private String announceDate;
    private double score;
    private boolean dateTbd;
    private String type;
    private String datetimeLocal;
    private String visibleUntilUtc;
    private boolean timeTbd;
    private List<taxonomies> taxonomies;
    private List<performers> performers;
    private String url;
    private String createdAt;
    private venue venue;
    private String shortTitle;
    private String datetimeUtc;
    private boolean datetimeTbd;

    public links[] getLinks() {
        return links;
    }

    public void setLinks(links[] links) {
        this.links = links;
    }

    public int getId(){
        return id;
    }
    public void setId(int input){
        this.id = input;
    }
    public stats getStats(){
        return stats;
    }
    public void setStats(stats input){
        this.stats = input;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String input){
        this.title = input;
    }
    public String getAnnounceDate(){
        return announceDate;
    }
    public void setAnnounceDate(String input){
        this.announceDate = input;
    }
    public double getScore(){
        return score;
    }
    public void setScore(double input){
        this.score = input;
    }
    public boolean getDateTbd(){
        return dateTbd;
    }
    public void setDateTbd(boolean input){
        this.dateTbd = input;
    }
    public String getType(){
        return type;
    }
    public void setType(String input){
        this.type = input;
    }
    public String getDatetimeLocal(){
        return datetimeLocal;
    }
    public void setDatetimeLocal(String input){
        this.datetimeLocal = input;
    }
    public String getVisibleUntilUtc(){
        return visibleUntilUtc;
    }
    public void setVisibleUntilUtc(String input){
        this.visibleUntilUtc = input;
    }
    public boolean getTimeTbd(){
        return timeTbd;
    }
    public void setTimeTbd(boolean input){
        this.timeTbd = input;
    }

    public List<taxonomies> getTaxonomies() {
        return taxonomies;
    }

    public void setTaxonomies(List<taxonomies> taxonomies) {
        this.taxonomies = taxonomies;
    }

    public List<com.ndanda.homeaway.data.performers> getPerformers() {
        return performers;
    }

    public void setPerformers(List<com.ndanda.homeaway.data.performers> performers) {
        this.performers = performers;
    }

    public String getUrl(){
        return url;
    }
    public void setUrl(String input){
        this.url = input;
    }
    public String getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(String input){
        this.createdAt = input;
    }
    public venue getVenue(){
        return venue;
    }
    public void setVenue(venue input){
        this.venue = input;
    }
    public String getShortTitle(){
        return shortTitle;
    }
    public void setShortTitle(String input){
        this.shortTitle = input;
    }
    public String getDatetimeUtc(){
        return datetimeUtc;
    }
    public void setDatetimeUtc(String input){
        this.datetimeUtc = input;
    }
    public boolean getDatetimeTbd(){
        return datetimeTbd;
    }
    public void setDatetimeTbd(boolean input){
        this.datetimeTbd = input;
    }
}
