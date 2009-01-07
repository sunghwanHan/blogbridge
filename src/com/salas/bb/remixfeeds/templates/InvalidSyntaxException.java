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
// $Id: InvalidSyntaxException.java,v 1.1 2008/02/29 13:45:51 spyromus Exp $
//

package com.salas.bb.remixfeeds.templates;

import java.util.List;

/**
 * Thrown when the template text with an invalid syntax is
 * trying to be set.
 */
public class InvalidSyntaxException extends IllegalArgumentException
{
    private List<SyntaxError> errors;

    /**
     * Creates an exception.
     *
     * @param errors syntax errors.
     */
    public InvalidSyntaxException(List<SyntaxError> errors)
    {
        this.errors = errors;
    }

    /**
     * Returns the list of syntax errors.
     *
     * @return errors.
     */
    public List<SyntaxError> getErrors()
    {
        return errors;
    }
}
