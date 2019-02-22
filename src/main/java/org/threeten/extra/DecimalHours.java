/*
 * Copyright (c) 2007-present, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.threeten.extra;

import java.io.Serializable;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.threeten.extra.temporal.DecimalUnit.HOURS;

/**
 * A decimal hour-based amount of time, such as '4.5 hours' instead of '4:30'.
 * <p>
 * This class models a quantity or amount of time in terms of decimal hours.
 * Decimal hours are commonly used for payroll and hourly billing systems.
 * In decimal time one hour has one hundred decimal minutes.
 * <h3>Implementation Notes:</h3>
 * Decimal hours use a a fixed scale of two digits to the right of the decimal point.
 * Two digits could hold 100 different values, but since one hour is 60 minutes we
 * have fractional part that stores only 60 values.
 *
 * <h3>Implementation Requirements:</h3>
 * This class is immutable and thread-safe.
 * <p>
 * This class must be treated as a value type. Do not synchronize, rely on the
 * identity hash code or use the distinction between equals() and ==.
 */
public final class DecimalHours
        implements TemporalAmount, Comparable<DecimalHours>, Serializable {
    
    /**
     *  Replaces computation from minutes to decimals with a simpler array index.
     */
    private static final int[] REMAINDER = {
        0,
        2,
        3,
        5,
        7,
        8,
        10,
        12,
        13,
        15,
        17,
        18,
        20,
        22,
        23,
        25,
        27,
        28,
        30,
        32,
        33,
        35,
        37,
        38,
        40,
        42,
        43,
        45,
        47,
        48,
        50,
        52,
        53,
        55,
        57,
        58,
        60,
        62,
        63,
        65,
        67,
        68,
        70,
        72,
        73,
        75,
        77,
        78,
        80,
        82,
        83,
        85,
        87,
        88,
        90,
        92,
        93,
        95,
        97,
        98
    };
    
    /**
     * The number of minutes in an decimal hour.
     */
    private static final int MINUTES_PER_DECIMAL_HOUR = 100;
    
    /**
     * The number of decimal minutes in this decimal hour
     */
    private final long decimal;
    
    public static DecimalHours of(long hours, long minutes) {
        int index = ChronoField.MINUTE_OF_HOUR.checkValidIntValue(minutes);
        long decimalMinutes = Math.addExact(
                Math.multiplyExact(hours, MINUTES_PER_DECIMAL_HOUR),
                REMAINDER[index]);
        return new DecimalHours(decimalMinutes);
    }
    
    public static DecimalHours from(double decimalHours) {
        long decimal = Double.valueOf(decimalHours * MINUTES_PER_DECIMAL_HOUR)
                .longValue();
        return new DecimalHours(decimal);
    }
    
    public static DecimalHours from(TemporalAmount amount) {
        if (amount instanceof DecimalHours) {
            return (DecimalHours) amount;
        }
        Objects.requireNonNull(amount, "amount");
        
        long hours   = 0;
        long minutes = 0;
        
        for (TemporalUnit unit : amount.getUnits()) {
            if (unit.equals(ChronoUnit.HOURS)) {
                hours = amount.get(ChronoUnit.HOURS);
            }
            if (unit.equals(ChronoUnit.MINUTES)) {
                minutes = amount.get(ChronoUnit.MINUTES);
            }
        }
        return of(hours, minutes);
    }
    
    private DecimalHours(long decimalMinutes) {
        this.decimal = decimalMinutes;
    }
    
    public double getAmount() {
        return decimal / MINUTES_PER_DECIMAL_HOUR;
    }
    
    @Override
    public long get(TemporalUnit unit) {
        if (unit == HOURS) {
            return decimal;
        }
        throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
    }

    @Override
    public List<TemporalUnit> getUnits() {
        return Collections.singletonList(HOURS);
    }

    @Override
    public Temporal addTo(Temporal temporal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public int compareTo(DecimalHours otherAmount) {
        return Long.compare(decimal, otherAmount.decimal);
    }

    @Override
    public String toString() {
        return String.valueOf(decimal);
    }
    
}
