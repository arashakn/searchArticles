package com.wonolo.nytimesseach.model

data class Article (val web_url : String, val snippet : String, val lead_paragraph :String , val abstract : String , val headline: Headline , val multimedia : ArrayList<Multimedia>)

data class Headline(val main : String)

data class Multimedia (val subtype: String, val url : String)

data class  Meta (val hits : Long, val offset : Int, val time : Int)

data class  Response(val docs : ArrayList<Article>, val meta : Meta  )

data class Articles(val response : Response , val meta: Meta)