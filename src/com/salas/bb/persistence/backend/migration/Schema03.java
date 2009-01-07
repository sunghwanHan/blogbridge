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
// $Id: Schema03.java,v 1.2 2006/12/13 09:00:14 spyromus Exp $
//

package com.salas.bb.persistence.backend.migration;

import com.salas.bb.persistence.backend.HsqlPersistenceManager;
import com.salas.bb.persistence.PersistenceException;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Connection;

/**
 * Migration from 3.7 to 3.8.
 */
public class Schema03 extends AbstractSchema
{
    /**
     * Migrates from some version to the other.
     *
     * @param con connection to use.
     * @param pm  persistence manager to use for data operations.
     *
     * @throws MigrationException in case of any problems with procedure.
     */
    public void perform(Connection con, HsqlPersistenceManager pm) throws MigrationException
    {
        addColumn(con, "ARTICLES", "PLAINTEXT VARCHAR(255)");
        addColumn(con, "ARTICLES", "SIMPLEMATCHKEY VARCHAR(16)");
    }
}
