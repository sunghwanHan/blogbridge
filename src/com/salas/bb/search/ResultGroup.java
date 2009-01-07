// BlogBridge -- RSS feed reader, manager, and web based service
// Copyright (C) 2002-2007 by R. Pito Salas
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
// $Id: ResultGroup.java,v 1.2 2007/06/07 14:17:41 spyromus Exp $
//

package com.salas.bb.search;

import com.salas.bb.utils.IdentityList;

/**
 * The group holder of items.
 */
public class ResultGroup<E extends ResultItem> extends IdentityList<E>
{
    private final Integer key;
    private final String name;

    /**
     * Creates a group with the key and name.
     *
     * @param key   key.
     * @param name  name.
     */
    public ResultGroup(Integer key, String name)
    {
        this.key = key;
        this.name = name;
    }

    /**
     * Returns the group key.
     *
     * @return key.
     */
    public Integer getKey()
    {
        return key;
    }

    /**
     * Returns the group name.
     *
     * @return name.
     */
    public String getName()
    {
        return name;
    }
}
