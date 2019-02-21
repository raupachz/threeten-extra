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
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;

/**
 * A decimal hour-based amount of time, such as '4.5 hours' instead of '4:30'.
 * <p>
 * This class models a quantity or amount of time in terms of decimal hours.
 * Decimal hours are commonly used for payroll and hourly billing.
 * <p>
 * The model is of a directed amount, meaning that the amount may be negative.
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
     * A serialization identifier for this class.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 1 hour = 100 minutes in base 10
     */
    private final int minutes;
    
    
    private DecimalHours(int minutes) {
        this.minutes = minutes;
    }
    
    //-----------------------------------------------------------------------
    /**
     * Gets the number of hours in this amount.
     *
     * @return the number of hours
     */
    public double getAmount() {
        return ((double) minutes) / 60.0;
    }
    
    @Override
    public long get(TemporalUnit unit) {
        return 0;
    }

    @Override
    public List<TemporalUnit> getUnits() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Temporal addTo(Temporal temporal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Temporal subtractFrom(Temporal temporal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int compareTo(DecimalHours otherAmount) {
        return minutes - otherAmount.minutes;
    }

    @Override
    public String toString() {
        return String.valueOf(getAmount());
    }
    
    public static DecimalHours of(double hours) {
        int minutes = (int) (100 * hours);
        return new DecimalHours(minutes);
    }
    
    
}
