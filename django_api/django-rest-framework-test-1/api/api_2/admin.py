from django.contrib import admin
from api_2.models import Order,OrderItem

class OrderItenInLine(admin.TabularInline):
    model = OrderItem

class OrderAdmin(admin.ModelAdmin):
    inlines = [
        OrderItenInLine
    ]

admin.site.register(Order, OrderAdmin)
