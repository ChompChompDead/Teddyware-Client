package com.teddyware.client.setting.settings;

import com.teddyware.client.Teddyware;
import com.teddyware.client.module.Module;
import com.teddyware.client.setting.Setting;

public class NumberSetting extends Setting implements com.lukflug.panelstudio.settings.NumberSetting {
    public double value;
    public double minimum;
    public double maximum;
    public double increment;

    public NumberSetting(String name, Module parent, double value, double minimum, double maximum, double increment) {
        this.name = name;
        this.parent = parent;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        double precision = 1.0D / this.increment;
        this.value = Math.round(Math.max(this.minimum, Math.min(this.maximum, value)) * precision) / precision;
        if (Teddyware.config != null) {
            Teddyware.config.save();
        }
    }

    public void increment(boolean positive) {
        setValue(getValue() + (positive?1:-1) * increment);
    }

    public double getMinimum() {
        return this.minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getMaximum() {
        return this.maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public double getIncrement() {
        return this.increment;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    @Override
    public double getNumber() {
        return this.value;
    }

    @Override
    public void setNumber(double value) {
        double precision = 1.0D / this.increment;
        this.value = Math.round(Math.max(this.minimum, Math.min(this.maximum, value)) * precision) / precision;
    }

    @Override
    public double getMaximumValue() {
        return this.maximum;
    }

    @Override
    public double getMinimumValue() {
        return this.minimum;
    }

    @Override
    public int getPrecision() {
        return 1;
    }
}
