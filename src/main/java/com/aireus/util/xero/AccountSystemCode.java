//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.28 at 10:40:40 PM EDT 
//


package com.aireus.util.xero;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for accountSystemCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="accountSystemCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="ROUNDING"/>
 *     &lt;enumeration value="GST"/>
 *     &lt;enumeration value="GSTONIMPORTS"/>
 *     &lt;enumeration value="CREDITORS"/>
 *     &lt;enumeration value="DEBTORS"/>
 *     &lt;enumeration value="UNPAIDEXPCLM"/>
 *     &lt;enumeration value="HISTORICAL"/>
 *     &lt;enumeration value="RETAINEDEARNINGS"/>
 *     &lt;enumeration value="TRACKINGTRANSFERS"/>
 *     &lt;enumeration value="REALISEDCURRENCYGAIN"/>
 *     &lt;enumeration value="UNREALISEDCURRENCYGAIN"/>
 *     &lt;enumeration value="BANKCURRENCYGAIN"/>
 *     &lt;enumeration value="WAGEPAYABLES"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "accountSystemCode")
@XmlEnum
public enum AccountSystemCode {


    /**
     * Rounding
     * 
     */
    ROUNDING,

    /**
     *  GST
     * 
     */
    GST,

    /**
     * GST On Imports
     * 
     */
    GSTONIMPORTS,

    /**
     * Creditors
     * 
     */
    CREDITORS,

    /**
     * Debtors
     * 
     */
    DEBTORS,

    /**
     * Unpaid Expense Claims
     * 
     */
    UNPAIDEXPCLM,

    /**
     * Historical Adjustments
     * 
     */
    HISTORICAL,

    /**
     * Retained Earnings
     * 
     */
    RETAINEDEARNINGS,

    /**
     * Tracking Transfers
     * 
     */
    TRACKINGTRANSFERS,

    /**
     * Realised FX
     * 
     */
    REALISEDCURRENCYGAIN,

    /**
     * Unrealised FX
     * 
     */
    UNREALISEDCURRENCYGAIN,

    /**
     * Bank Revaluations
     * 
     */
    BANKCURRENCYGAIN,

    /**
     * Wage Payables
     * 
     */
    WAGEPAYABLES;

    public String value() {
        return name();
    }

    public static AccountSystemCode fromValue(String v) {
        return valueOf(v);
    }

}
