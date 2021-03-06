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
// $Id: DiggQueryType.java,v 1.2 2006/12/11 13:54:37 spyromus Exp $
//

package com.salas.bb.domain.querytypes;

import com.salas.bb.domain.FeedType;
import com.salas.bb.utils.ResourceID;
import com.salas.bb.utils.StringUtils;
import com.salas.bb.utils.uif.BBFormBuilder;
import com.salas.bb.utils.i18n.Strings;
import com.salas.bb.views.feeds.IFeedDisplayConstants;
import com.salas.bb.twitter.TwitterFeature;
import com.salas.bb.twitter.TwitterPreferences;
import com.salas.bb.core.GlobalController;

import javax.swing.*;
import java.text.MessageFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Twitter query type.
 */
class TwitterQueryType extends DefaultQueryType
{
    private static final String PATTERN_QUERY   = "http://search.twitter.com/search.atom?q={0}&rpp={1}";
    private static final String PATTERN_FRIENDS = "http://{0}:{1}@twitter.com/statuses/friends_timeline/{0}.rss";

    /**
     * Creates name query type with associated icon and URL pattern. The pattern can use the
     * formatting rules of <code>MessageFormat</code> class and keep in mind that:
     * <ul>
     * <li><b>{0]</b> is a list of parameters, separated with "+" and properly escaped for
     * use in URL.</li>
     * <li><b>{1}</b> maximum number of articles to fetch.</li>
     * </ul>
     *
     * @throws NullPointerException if <code>aName</code> or <code>anURLPattern</code>
     *                              is not specified.
     */
    protected TwitterQueryType()
    {
        super(TYPE_TWITTER, FeedType.TWITTER,
            Strings.message("queryfeed.type.twitter.name"),
            ResourceID.ICON_QUERY_FEED_TWITTER,
            "",
            Strings.message("queryfeed.type.twitter.parameter"),
            "",
            Strings.message("queryfeed.type.twitter.description"),
            IFeedDisplayConstants.MODE_FULL);
    }

    @Override
    protected String formURLString(String param, int limit)
    {
        String url;

        if ("~~".equals(param))
        {
            if (!TwitterFeature.isConfigured()) return null;

            TwitterPreferences tp = GlobalController.SINGLETON.getModel().getUserPreferences().getTwitterPreferences();
            String username = tp.getScreenName();
            String password = tp.getPassword();

            url = MessageFormat.format(PATTERN_FRIENDS, username, password);
        } else
        {
            param = parameterToURLPart(param);
            if (StringUtils.isEmpty(param)) return null;
            url = MessageFormat.format(PATTERN_QUERY, param, limit);
        }

        return url;
    }

    @Override
    public QueryEditorPanel getEditorPanel(int labelColWidth)
    {
        return new QueryEditor(labelColWidth);
    }

    /**
     * Microtag query editor.
     */
    private static class QueryEditor extends QueryEditorPanel
    {
        private static final int    FRIENDS         = 0;
        private static final int    QUERY           = 1;

        private static final String TYPE_FRIENDS    = "Friends Timeline";
        private static final String TYPE_QUERY      = "Query";

        private final JLabel     lbQuery;
        private final JComboBox  cbType;
        private final JTextField tfQuery;

        /**
         * Creates the editor.
         *
         * @param labelColWidth the width in 'dlu' of the label column.
         */
        private QueryEditor(int labelColWidth)
        {
            lbQuery = new JLabel("Query:");
            cbType = new JComboBox();
            tfQuery = new JTextField();

            // Initialize types
            cbType.addItem(TYPE_FRIENDS);
            cbType.addItem(TYPE_QUERY);

            BBFormBuilder b = new BBFormBuilder(labelColWidth + "dlu, 4dlu, p, p:grow", this);
            b.append("Type:", cbType);
            b.append(lbQuery);
            b.append(tfQuery, 2);

            cbType.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    updateFieldState();
                }
            });

            updateFieldState();
        }

        /**
         * Updates the state of fields.
         */
        private void updateFieldState()
        {
            boolean en = cbType.getSelectedIndex() == QUERY;
            lbQuery.setEnabled(en);
            tfQuery.setEnabled(en);
        }

        /**
         * Sets the value of the parameter. Initializes the internal controls with
         * the values deciphered from the parameter given.
         *
         * @param text the text.
         */
        public void setParameter(String text)
        {
            int type;
            String query;

            if ("~~".equals(text))
            {
                type = FRIENDS;
                query = null;
            } else
            {
                type = QUERY;
                query = text;
            }

            cbType.setSelectedIndex(type);
            tfQuery.setText(query);
        }

        /**
         * Returns the value of the parameter.
         *
         * @return the text.
         */
        public String getParameter()
        {
            return cbType.getSelectedIndex() == FRIENDS ? "~~" : tfQuery.getText();
        }
    }
}