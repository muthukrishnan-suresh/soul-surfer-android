package com.soulsurfer.android.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public enum MetaAttr {
    TWITTER_TITLE(Type.TITLE, "twitter:title", Source.TWITTER),
    TWITTER_DESCRIPTION(Type.DESCRIPTION, "twitter:description", Source.TWITTER),
    TWITTER_IMAGE(Type.IMAGE, "twitter:image", Source.TWITTER),

    OG_TITLE(Type.TITLE, "og:title", Source.OG),
    OG_DESCRIPTION(Type.DESCRIPTION, "og:description", Source.OG),
    OG_IMAGE(Type.IMAGE, "og:image", Source.OG),
    OG_IMAGE_WIDTH(Type.WIDTH, "og:image:width", Source.OG),
    OG_IMAGE_HEIGHT(Type.HEIGHT, "og:image:height", Source.OG),
    OG_SITE_NAME(Type.SITE_NAME, "og:site_name", Source.OG),

    HTML_DESCRIPTION(Type.DESCRIPTION, "description", Source.HTML);

    public enum Type {
        TITLE,
        DESCRIPTION,
        IMAGE,
        WIDTH,
        HEIGHT,
        SITE_NAME
    }

    public enum Source {
        TWITTER(1),
        OG(2),
        HTML(3);

        private int priority;

        Source(int priority) {
            this.priority = priority;
        }

        public static List<Source> getPriotitzedSourceList() {
            TreeMap<Integer, Source> sortedSources = new TreeMap<>();
            for (Source s : Source.values()) {
                sortedSources.put(s.priority, s);
            }
            return new ArrayList<>(sortedSources.values());
        }
    }

    private Type type;

    private String name;

    private Source source;

    private static HashMap<Type, List<MetaAttr>> metaTypeMap = new HashMap<>();
    private static HashMap<String, MetaAttr> metaAttrMap = new HashMap<>();

    static {
        try {
            HashMap<Source, HashMap<Type, MetaAttr>> map = new HashMap<>();

            for (MetaAttr metaAttr : MetaAttr.values()) {
                HashMap<Type, MetaAttr> typeMap = map.get(metaAttr.source);
                if (typeMap == null) {
                    typeMap = new HashMap<>();
                }

                typeMap.put(metaAttr.type, metaAttr);
                map.put(metaAttr.source, typeMap);
                metaAttrMap.put(metaAttr.name, metaAttr);
            }

            for (Type t : Type.values()) {
                List<MetaAttr> metaAttrs = metaTypeMap.get(t);
                if (metaAttrs == null) {
                    metaAttrs = new ArrayList<>();
                }

                for (Source s : Source.getPriotitzedSourceList()) {
                    HashMap<Type, MetaAttr> typeMap = map.get(s);
                    if (typeMap != null && typeMap.containsKey(t)) {
                        metaAttrs.add(typeMap.get(t));
                    }
                }
                metaTypeMap.put(t, metaAttrs);
            }
        } catch (Exception e) {
            Log.e("SoulSurfer", e.toString());
        }
    }

    MetaAttr(Type type, String name, Source source) {
        this.type = type;
        this.name = name;
        this.source = source;
    }

    public static HashMap<String, MetaAttr> getMetaAttrMap() {
        return metaAttrMap;
    }

    public static List<MetaAttr> getAttrForType(Type type) {
        return metaTypeMap.get(type);
    }
}
