from django.contrib import admin
from .models import Kategoria, Produkt, Klient, Zamowienie, PozycjaZamowienia


admin.site.register(Kategoria)
admin.site.register(Produkt)
admin.site.register(Klient)
admin.site.register(Zamowienie)
admin.site.register(PozycjaZamowienia)
