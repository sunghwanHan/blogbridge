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
// $Id: StringVE.java,v 1.4 2006/01/08 05:13:00 kyank Exp $
//

package com.salas.bb.views.querybuilder.editors;

import com.jgoodies.binding.adapter.DocumentAdapter;
import com.jgoodies.binding.value.ValueModel;

import javax.swing.*;
import java.awt.*;

/**
 * Simple string value editor.
 */
class StringVE extends JTextField implements IValueEditor
{
    /**
     * Constructs editor for some value model.
     *
     * @param aModel editor.
     */
    public StringVE(ValueModel aModel)
    {
        setDocument(new DocumentAdapter(aModel));
    }

    /**
     * Returns the component to be used for displaying the editor.
     *
     * @return component.
     */
    public Component getVisualComponent()
    {
        return this;
    }
}
