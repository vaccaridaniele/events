# -*- coding: utf-8 -*-
import scrapy


class EventibzSpider(scrapy.Spider):
    name = "eventibz"
    allowed_domains = ["http://www.bzup.it/category/musica/"]
    start_urls = ['http://http://www.bzup.it/category/musica//']

    def parse(self, response):
        pass
