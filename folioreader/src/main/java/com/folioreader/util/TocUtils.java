package com.folioreader.util;

import android.util.Log;

import com.folioreader.AppContext;
import com.folioreader.model.TOCLinkWrapper;

import org.readium.r2.shared.Link;
import org.readium.r2.shared.Publication;

import java.util.ArrayList;
import java.util.List;

public class TocUtils {
    private TocUtils(){
        _titleList = new ArrayList<String>();

    }
    private static TocUtils _tocutils = null;
    private boolean _isPopulate = false;
    private ArrayList<String> _titleList;
    public static TocUtils get() {
        if (_tocutils == null) {
            synchronized (TocUtils.class) {
                if (_tocutils == null) {
                    if (AppContext.get() == null) {
                        throw new IllegalStateException("-> context == null");
                    }
                    _tocutils = new TocUtils();
                }
            }
        }
        return _tocutils;
    }
    public void clear() {
        _isPopulate = false;
        _titleList.clear();
    }
    public void populateTOC(Publication publication) {
        if (publication == null) return;
        if (_isPopulate) return;
        _isPopulate = true;
        _titleList.clear();
        if (!publication.getTableOfContents().isEmpty()) {
            for (Link tocLink : publication.getTableOfContents()) {
                createTocLinkWrapper(tocLink, 0);
                //tocLinkWrappers.add(tocLinkWrapper);
            }
        }else {
            createTOCFromSpine(publication.getReadingOrder());
        }

    }
    public String getTitle (int index) {
      //  Log.d("TOCUTIL", "index " + index + " size "+ _titleList.size());
        return _titleList.get(index);
    }
    private void createTOCFromSpine(List<Link> spine) {
        Log.d("createtocfrom", "count " + spine.size());
        for (Link link : spine) {
            Log.d("createTOCFromSpine", link.getTitle());
            _titleList.add(link.getTitle());
        }
    }
    private void createTocLinkWrapper(Link tocLink, int indentation) {
       // Log.d("createTocLinkWrapper", tocLink.getTitle());
        _titleList.add(tocLink.getTitle());

        for (Link tocLink1 : tocLink.getChildren()) {
            createTocLinkWrapper(tocLink1, indentation + 1);
        }
    }

}
