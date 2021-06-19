package ufp.uczelnianeforumproblemowe.mvc.modelViews;

import ufp.uczelnianeforumproblemowe.jpa.enums.WydzialEnum;

public class WydzialView {

    private WydzialEnum wydzialEnum;

    public WydzialView() {}

    public WydzialEnum getWydzialEnum() {
        return wydzialEnum;
    }

    public void setWydzialEnum(WydzialEnum wydzialEnum) {
        this.wydzialEnum = wydzialEnum;
    }
}
