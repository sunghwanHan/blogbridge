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
// $Id: HTMLFeedDisplay.java,v 1.56 2008/02/28 15:59:45 spyromus Exp $
//

package com.salas.bb.views.feeds.html;

import com.jgoodies.binding.value.ValueModel;
import com.salas.bb.domain.IArticle;
import com.salas.bb.domain.IFeed;
import com.salas.bb.utils.i18n.Strings;
import com.salas.bb.utils.uif.html.CustomHTMLEditorKit;
import com.salas.bb.views.feeds.AbstractFeedDisplay;
import com.salas.bb.views.feeds.IArticleDisplay;
import com.salas.bb.views.feeds.IFeedDisplayConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Traditional HTML feed view.
 */
public class HTMLFeedDisplay extends AbstractFeedDisplay
    implements IFeedJumpLinkClickCallback
{
    private static final Logger LOG = Logger.getLogger(HTMLFeedDisplay.class.getName());

    /** Selected article property. */
    public static final String PROP_SELECTED_ARTICLE    = "selectedArticle";
    /** Hovered link property. */
    public static final String PROP_HOVERED_LINK        = "hoveredLink";

    private final IHTMLFeedDisplayConfig    htmlConfig;

    /**
     * Creates feed view.
     *
     * @param aConfig configuration of the view.
     * @param pageModel page model to update when page changes.
     * @param pageCountModel page model with the number of pages (updated by the FeedDisplayModel).
     */
    public HTMLFeedDisplay(IHTMLFeedDisplayConfig aConfig, ValueModel pageModel, ValueModel pageCountModel)
    {
        super(aConfig, pageModel, pageCountModel);

        htmlConfig = aConfig;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (ArticlesGroup group : groups) add(group);

        add(noContentPanel);
        
        updateMaxArticleAge();

        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
    }

    /**
     * Returns currently selected text in currently selected article.
     *
     * @return text.
     */
    public String getSelectedText()
    {
        return selectedDisplay == null
            ? null
            : ((HTMLArticleDisplay)selectedDisplay).getSelectedText();
    }

    /**
     * Returns message to show when there's no articles to display.
     *
     * @return the message.
     */
    protected String getNoContentPanelMessage()
    {
        String msg;

        int filter = getConfig().getFilter();
        switch (filter)
        {
            case IFeedDisplayConstants.FILTER_PINNED:
                msg = Strings.message("feeddisplay.no.pinned.articles");
                break;
            case IFeedDisplayConstants.FILTER_UNREAD:
                msg = Strings.message("feeddisplay.no.unread.articles");
                break;
            default:
                msg = Strings.message("feeddisplay.no.articles");
        }

        return msg;
    }

    /**
     * Creates new article display for addition to the display.
     *
     * @param aArticle article to create display for.
     *
     * @return display.
     */
    protected IArticleDisplay createNewArticleDisplay(IArticle aArticle)
    {
        IFeed loadedFeed = model.getFeed();
        boolean smartFeed = loadedFeed != aArticle.getFeed();

        IArticleDisplayConfig articleConfig = htmlConfig.getArticleViewConfig();

        return new HTMLArticleDisplay(aArticle, articleConfig, smartFeed, this,
            new CustomHTMLEditorKit());
    }

    /**
     * Returns current logger.
     *
     * @return logger object.
     */
    protected Logger getLogger()
    {
        return LOG;
    }

    // --------------------------------------------------------------------------------------------
    // Keyboard events
    // --------------------------------------------------------------------------------------------

    /**
     * Feed jump link clicked.
     *
     * @param feed feed.
     */
    public void onFeedJumpLinkClicked(IFeed feed)
    {
        fireFeedJumpLinkClicked(feed);
    }

    // --------------------------------------------------------------------------------------------
    // Events
    // --------------------------------------------------------------------------------------------

    @Override
    protected void processKeyEvent(KeyEvent e)
    {
        boolean isCollapsing = e.getKeyCode() == KeyEvent.VK_LEFT;

        if (e.getID() == KeyEvent.KEY_PRESSED &&
            (isCollapsing || e.getKeyCode() == KeyEvent.VK_RIGHT))
        {
            cycleViewMode(e.isControlDown(), !isCollapsing);
        } else if (e.getID() == KeyEvent.KEY_PRESSED &&
                e.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET)
        {
            // DEBUG sequence
            model.dump();

            Component[] cs = getComponents();
            LOG.warning("--- List Dump ---");
            LOG.warning("Number of components: " + cs.length);
            for (Component c : cs)
            {
                if (c instanceof HTMLArticleDisplay)
                {
                    LOG.warning("  " + ((HTMLArticleDisplay)c).getArticle().getTitle());
                } else
                {
                    LOG.warning("  - Group: " + c.getName());
                }
            }
        } else
        {
            super.processKeyEvent(e);
        }
    }

    /**
     * Returns the component the user clicked on.
     *
     * @param e event.
     *
     * @return component.
     */
    protected Object getComponentForMouseEvent(MouseEvent e)
    {
        return getComponentAt(e.getPoint());
    }

    /**
     * Returns the view popup adapter.
     *
     * @return view popup adapter.
     */
    protected MouseListener getViewPopupAdapter()
    {
        return htmlConfig.getViewPopupAdapter();
    }

    /**
     * Returns the link popup adapter.
     *
     * @return link popup adapter.
     */
    protected MouseListener getLinkPopupAdapter()
    {
        return htmlConfig.getLinkPopupAdapter();
    }

    /**
     * Repaints article text if is currently in the given mode.
     *
     * @param briefMode <code>TRUE</code> for brief mode, otherwise -- full mode.
     */
    public void repaintIfInMode(boolean briefMode)
    {
        Iterator it = new ArticleDisplayIterator();
        while (it.hasNext())
        {
            HTMLArticleDisplay display = (HTMLArticleDisplay)it.next();
            display.repaintIfInMode(briefMode);
        }
    }

    /**
     * Invoked on date visibility change.
     *
     * TODO deprecated
     */
    private void onDateStatusChange()
    {
        Iterator it = new ArticleDisplayIterator();
        while (it.hasNext())
        {
            HTMLArticleDisplay display = (HTMLArticleDisplay)it.next();
            display.updateDateStatus();
        }
    }

    /**
     * Invoked on view mode layout changes.
     */
    private void onViewModeLayoutChange()
    {
        Iterator it = new ArticleDisplayIterator();
        while (it.hasNext())
        {
            HTMLArticleDisplay display = (HTMLArticleDisplay)it.next();
            display.updateComponentsState();
        }
    }

    /**
     * Sets max article age.
     */
    private void updateMaxArticleAge()
    {
        int maxAge = htmlConfig.isSuppressingOld() ? htmlConfig.getMaxArticleAge() * 24 * 3600000 : -1;
        model.setMaxArticleAge(maxAge);
    }

    /**
     * Returns tool-tip for a give link.
     *
     * @param link link.
     * @param textPane      pane requesting the tooltip.
     *
     * @return tool-tip text.
     */
    protected String getHoveredLinkTooltip(URL link, JComponent textPane)
    {
        return link == null ? null : htmlConfig.getArticleViewConfig().getLinkTooltip(link);
    }

    /**
     * Invoked when config property changes.
     *
     * @param name name of the property.
     */
    protected void onConfigPropertyChange(String name)
    {
        if (IHTMLFeedDisplayConfig.SHOW_DATE.equals(name))
        {
            onDateStatusChange();
        } else if (IHTMLFeedDisplayConfig.SUPPRESS_OLD.equals(name) ||
            IHTMLFeedDisplayConfig.MAX_ARTICLE_AGE.equals(name))
        {
            updateMaxArticleAge();
        } else if (IHTMLFeedDisplayConfig.VIEW_MODE_LAYOUT.equals(name))
        {
            onViewModeLayoutChange();
        } else
        {
            super.onConfigPropertyChange(name);
        }
    }
}
