// BlogBridge -- RSS feed reader, manager, and web based service
// Copyright (C) 2002-2006 by R. Pito Salas
//
// This program is free software; you can redistribute it and/or modify it under
// the terms of the GNU General Public License as published by the Free Software Foundation;
// either version 2 of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
// without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
// See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along with this program;
// if not, write to the Free Software Foundation, Inc., 59 Temple Place,
// Suite 330, Boston, MA 02111-1307 USA
//
// Contact: R. Pito Salas
// mailto:pitosalas@users.sourceforge.net
// More information: about BlogBridge
// http://www.blogbridge.com
// http://sourceforge.net/projects/blogbridge
//
// $Id: ArticleSentimentsProperty.java,v 1.2 2008/02/28 10:50:24 spyromus Exp $
//

package com.salas.bb.domain.query.articles;

import com.salas.bb.domain.IArticle;
import com.salas.bb.domain.query.AbstractProperty;
import com.salas.bb.domain.query.IComparisonOperation;
import com.salas.bb.domain.query.PropertyType;
import com.salas.bb.domain.query.general.StringEqualsCO;
import com.salas.bb.domain.query.general.StringNotEqualsCO;
import com.salas.bb.utils.i18n.Strings;

/**
 * Article sentiments property represents two possible statuses: positive and negative.
 */
public class ArticleSentimentsProperty extends AbstractProperty
{
    /** Instance of this property. */
    public static final ArticleSentimentsProperty INSTANCE = new ArticleSentimentsProperty();

    /** Positive value. */
    public static final String VALUE_POSITIVE = "positive";
    /** Negative value. */
    public static final String VALUE_NEGATIVE = "negative";
    /** Neutral value. */
    public static final String VALUE_NEUTRAL = "neutral";

    /**
     * Creates property.
     */
    public ArticleSentimentsProperty()
    {
        super(Strings.message("query.property.article.sentiment"),
            "article-sentiments", PropertyType.SENTIMENTS, VALUE_POSITIVE,
            new IComparisonOperation[] { StringEqualsCO.INSTANCE, StringNotEqualsCO.INSTANCE });
    }

    /**
     * Compares the corresponding property of the target object to the value using specific
     * comparison operation.
     *
     * @param target    target object.
     * @param operation comparison operation, supported by this property.
     * @param value     value to compare against.
     *
     * @return TRUE if the object matches the criteria.
     *
     * @throws NullPointerException     if target, operation or value are NULL's.
     * @throws ClassCastException       if target is not supported.
     * @throws IllegalArgumentException if operation is not supported.
     */
    public boolean match(Object target, IComparisonOperation operation, String value)
    {
        IArticle article = (IArticle)target;
        String targetValue = sentimentsToValue(article);

        return operation.match(targetValue, value);
    }

    private String sentimentsToValue(IArticle aArticle)
    {
        return aArticle.isPositive() ? VALUE_POSITIVE : aArticle.isNegative() ? VALUE_NEGATIVE : VALUE_NEUTRAL;
    }
}