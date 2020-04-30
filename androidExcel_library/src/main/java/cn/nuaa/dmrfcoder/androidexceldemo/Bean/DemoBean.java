package cn.nuaa.dmrfcoder.androidexceldemo.Bean;

/**
 * @author dmrfcoder
 * @date 2019/2/14
 */

public class DemoBean {
    int 倍率;
    int 胜场;
    double 收益倍率;

    public DemoBean(int 倍率, int 胜场, double 收益倍率) {
        this.倍率 = 倍率;
        this.胜场 = 胜场;
        this.收益倍率 = 收益倍率;
    }

    public int get倍率() {
        return 倍率;
    }

    public void set倍率(int 倍率) {
        this.倍率 = 倍率;
    }

    public int get胜场() {
        return 胜场;
    }

    public void set胜场(int 胜场) {
        this.胜场 = 胜场;
    }

    public double get收益倍率() {
        return 收益倍率;
    }

    public void set收益倍率(double 收益倍率) {
        this.收益倍率 = 收益倍率;
    }
}
