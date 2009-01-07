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
// $Id: IComparisonOperation.java,v 1.3 2007/02/20 15:15:55 spyromus Exp $
//

package com.salas.bb.domain.query;

/**
 * Comparison operation is some operation for comparison of two values. The examples of
 * such operations are: less, equal, bigger and their combinations, is (not) empty and etc.
 */
public interface IComparisonOperation
{
    /**
     * Comparison operation name.
     *
     * @return name.
     */
    String getName();

    /**
     * Comparison operation descriptor.
     *
     * @return descriptor.
     */
    String getDescriptor();

    /**
     * Compares some target value against value for comparison.
     *
     * @param targetValue       target value.
     * @param comparisonValue   comparison value.
     *
     * @return TRUE if the target value matches the condition presented by this comparison
     *         operation in conjunction with comparison value.
     */
    boolean match(String targetValue, String comparisonValue);
}
