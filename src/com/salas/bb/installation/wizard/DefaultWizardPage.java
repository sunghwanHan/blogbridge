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
// $Id: DefaultWizardPage.java,v 1.2 2006/01/08 04:53:13 kyank Exp $
//

package com.salas.bb.installation.wizard;

import javax.swing.*;

/**
 * Default implementation of wizard page which always returns success on validation.
 */
class DefaultWizardPage extends JPanel implements IWizardPage
{
    /**
     * Validates and returns error message to display or <code>null</code> if information on the
     * page is valid.
     *
     * @return string to display or <code>null</code>.
     */
    public String validatePage()
    {
        return null;
    }

    /**
     * Returns component to be put on the screen to represent this page.
     *
     * @return visible component.
     */
    public JComponent getComponent()
    {
        return this;
    }
}
