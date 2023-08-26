<h1 align="center">
  <a href="https://reactnative.dev/">
    Soul Surfer
  </a>
</h1>

<p align="center">
  <strong>Show intuitive previews of links</strong><br>
 Free. Fast. Efficient
</p>

Soul Surfer provides PageInfo of a giving link. This PageInfo is extracted from multiple sources and an unique algorithm is applied to extract the most data out of them quickly.

Using this PageInfo, you can show intuitive previews of links in your applications like whatsapp, facebook, slack, etc.

Test Commit


<img src="https://github.com/muthukrishnan-suresh/soul-surfer-android/blob/master/documentation/doc_images/other_apps_preview.png" alt="Other apps link preview image" />

## PageInfo
#### Input
https://youtu.be/W3mwSnF1n50
#### Output                                                                                                                                                                                                                                                                                                                                                                                                                                           
```json
{
  "url": "https://youtu.be/W3mwSnF1n50",
  "title": "Secure Your Data - Deep Dive into Encryption and Security (Android Dev Summit '19)",
  "description": "Strategies for data encryption on Android using Jetpack Security, key takeaways: learn to encrypt data safely on device and use the AndroidKeyStore. 1. Outli...",
  "imageUrl": "https://i.ytimg.com/vi/W3mwSnF1n50/hqdefault.jpg",
  "providerName": "YouTube",
  "providerIcon": "https://s.ytimg.com/yts/img/favicon_32-vflOogEID.png",
  "type": "VIDEO",
  "imageHeight": 360,
  "imageWidth": 480,
  "html": "<iframe width:\"480\" height:\"270\" src:\"https://www.youtube.com/embed/W3mwSnF1n50?feature:oembed\" frameborder:\"0\" allow:\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>"
}
```
#### Note
Soul Surfer uses data from OEmbed provider and Meta details present in the link to extract these details. If link details are not exposed in these sources, PageInfo will not be available.

## Integration Details
1. Add the jitpack URL to the root build.gradle (project -> build.gradle)
```
maven { url "https://jitpack.io" }
```
2. Add Soul Surfer dependency to your app module's build.gradle file (project -> app -> build.gradle):
```
implementation 'com.github.muthukrishnan-suresh:soul-surfer-android:1.0.0'
```
3. Add code to load PageInfo for links
```java
SoulSurfer.get(link)
                    .load(new PageInfoListener() {
                        @Override
                        public void onPageInfoLoaded(PageInfo pageInfo) {
                            // ... update UI
                        }

                        @Override
                        public void onError(String url) {
                            // handle errors
                        }
                    });
```

## References
https://oembed.com

https://medium.com/slack-developer-blog/everything-you-ever-wanted-to-know-about-unfurling-but-were-afraid-to-ask-or-how-to-make-your-e64b4bb9254
