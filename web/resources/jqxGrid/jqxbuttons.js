/*
 jQWidgets v3.2.0 (2014-Feb-01)
 Copyright (c) 2011-2014 jQWidgets.
 License: http://jqwidgets.com/license/
 */

(function (a) {
    a.jqx.cssroundedcorners = function (b) {
        var c = {all: "jqx-rc-all", top: "jqx-rc-t", bottom: "jqx-rc-b", left: "jqx-rc-l", right: "jqx-rc-r", "top-right": "jqx-rc-tr", "top-left": "jqx-rc-tl", "bottom-right": "jqx-rc-br", "bottom-left": "jqx-rc-bl"};
        for (prop in c) {
            if (!c.hasOwnProperty(prop)) {
                continue
            }
            if (b == prop) {
                return c[prop]
            }
        }
    };
    a.jqx.jqxWidget("jqxButton", "", {});
    a.extend(a.jqx._jqxButton.prototype, {defineInstance: function () {
        this.cursor = "arrow";
        this.roundedCorners = "all";
        this.disabled = false;
        this.height = null;
        this.width = null;
        this.overrideTheme = false;
        this.enableHover = true;
        this.enableDefault = true;
        this.enablePressed = true;
        this.rtl = false;
        this._ariaDisabled = false;
        this._scrollAreaButton = false;
        this.aria = {"aria-disabled": {name: "disabled", type: "boolean"}}
    }, createInstance: function (d) {
        var b = this;
        this._setSize();
        if (!this._ariaDisabled) {
            this.host.attr("role", "button")
        }
        if (!this.overrideTheme) {
            this.host.addClass(this.toThemeProperty(a.jqx.cssroundedcorners(this.roundedCorners)));
            if (this.enableDefault) {
                this.host.addClass(this.toThemeProperty("jqx-button"))
            }
            this.host.addClass(this.toThemeProperty("jqx-widget"))
        }
        this.isTouchDevice = a.jqx.mobile.isTouchDevice();
        if (!this._ariaDisabled) {
            a.jqx.aria(this)
        }
        if (this.cursor != "arrow") {
            if (!this.disabled) {
                this.host.css({cursor: this.cursor})
            } else {
                this.host.css({cursor: "arrow"})
            }
        }
        var g = "mouseenter mouseleave mousedown focus blur";
        if (this._scrollAreaButton) {
            var g = "mousedown"
        }
        if (this.isTouchDevice) {
            this.addHandler(this.host, a.jqx.mobile.getTouchEventName("touchstart"), function (h) {
                b.isPressed = true;
                b.refresh()
            });
            this.addHandler(a(document), a.jqx.mobile.getTouchEventName("touchend") + "." + this.element.id, function (h) {
                b.isPressed = false;
                b.refresh()
            })
        }
        this.addHandler(this.host, g, function (h) {
            switch (h.type) {
                case"mouseenter":
                    if (!this.isTouchDevice) {
                        if (!b.disabled && b.enableHover) {
                            b.isMouseOver = true;
                            b.refresh()
                        }
                    }
                    break;
                case"mouseleave":
                    if (!this.isTouchDevice) {
                        if (!b.disabled && b.enableHover) {
                            b.isMouseOver = false;
                            b.refresh()
                        }
                    }
                    break;
                case"mousedown":
                    if (!b.disabled) {
                        b.isPressed = true;
                        b.refresh()
                    }
                    break;
                case"focus":
                    if (!b.disabled) {
                        b.isFocused = true;
                        b.refresh()
                    }
                    break;
                case"blur":
                    if (!b.disabled) {
                        b.isFocused = false;
                        b.refresh()
                    }
                    break
            }
        });
        this.mouseupfunc = function (h) {
            if (!b.disabled) {
                b.isPressed = false;
                b.refresh()
            }
        };
        this.addHandler(a(document), "mouseup.button" + this.element.id, this.mouseupfunc);
        try {
            if (document.referrer != "" || window.frameElement) {
                if (window.top != null && window.top != window.self) {
                    var f = "";
                    if (window.parent && document.referrer) {
                        f = document.referrer
                    }
                    if (f.indexOf(document.location.host) != -1) {
                        var e = function (h) {
                            b.isPressed = false;
                            b.refresh()
                        };
                        if (window.top.document) {
                            this.addHandler(a(window.top.document), "mouseup", e)
                        }
                    }
                }
            }
        } catch (c) {
        }
        this.propertyChangeMap.roundedCorners = function (h, j, i, k) {
            h.host.removeClass(h.toThemeProperty(a.jqx.cssroundedcorners(i)));
            h.host.addClass(h.toThemeProperty(a.jqx.cssroundedcorners(k)))
        };
        this.propertyChangeMap.width = function (h, j, i, k) {
            h._setSize();
            h.refresh()
        };
        this.propertyChangeMap.height = function (h, j, i, k) {
            h._setSize();
            h.refresh()
        };
        this.propertyChangeMap.disabled = function (h, j, i, k) {
            if (i != k) {
                h.host[0].disabled = k;
                h.host.attr("disabled", k);
                h.refresh();
                if (!k) {
                    h.host.css({cursor: h.cursor})
                } else {
                    h.host.css({cursor: "default"})
                }
                a.jqx.aria(h, "aria-disabled", h.disabled)
            }
        };
        this.propertyChangeMap.rtl = function (h, j, i, k) {
            if (i != k) {
                h.refresh()
            }
        };
        this.propertyChangeMap.theme = function (h, j, i, k) {
            h.host.removeClass();
            h.host.addClass(h.toThemeProperty("jqx-button"));
            h.host.addClass(h.toThemeProperty("jqx-widget"));
            if (!h.overrideTheme) {
                h.host.addClass(h.toThemeProperty(a.jqx.cssroundedcorners(h.roundedCorners)))
            }
            h._oldCSSCurrent = null;
            h.refresh()
        };
        if (this.disabled) {
            this.element.disabled = true;
            this.host.attr("disabled", true)
        }
    }, resize: function (c, b) {
        this.width = c;
        this.height = b;
        this._setSize()
    }, val: function () {
        var b = this.host.find("input");
        if (b.length > 0) {
            if (arguments.length == 0 || typeof(value) == "object") {
                return b.val()
            }
            b.val(value);
            this.refresh();
            return b.val()
        }
        if (arguments.length == 0 || typeof(value) == "object") {
            if (this.element.nodeName.toLowerCase() == "button") {
                return a(this.element).text()
            }
            return this.element.value
        }
        this.element.value = arguments[0];
        if (this.element.nodeName.toLowerCase() == "button") {
            a(this.element).text(arguments[0])
        }
        this.refresh()
    }, _setSize: function () {
        if (this.width != null && (this.width.toString().indexOf("px") != -1 || this.width.toString().indexOf("%") != -1)) {
            this.host.css("width", this.width)
        } else {
            if (this.width != undefined && !isNaN(this.width)) {
                this.host.css("width", this.width)
            }
        }
        if (this.height != null && (this.height.toString().indexOf("px") != -1 || this.height.toString().indexOf("%") != -1)) {
            this.host.css("height", this.height)
        } else {
            if (this.height != undefined && !isNaN(this.height)) {
                this.host.css("height", parseInt(this.height))
            }
        }
    }, _removeHandlers: function () {
        this.removeHandler(this.host, "selectstart");
        this.removeHandler(this.host, "click");
        this.removeHandler(this.host, "focus");
        this.removeHandler(this.host, "blur");
        this.removeHandler(this.host, "mouseenter");
        this.removeHandler(this.host, "mouseleave");
        this.removeHandler(this.host, "mousedown");
        this.removeHandler(a(document), "mouseup.button" + this.element.id, this.mouseupfunc);
        if (this.isTouchDevice) {
            this.removeHandler(this.host, a.jqx.mobile.getTouchEventName("touchstart"));
            this.removeHandler(a(document), a.jqx.mobile.getTouchEventName("touchend") + "." + this.element.id)
        }
        this.mouseupfunc = null;
        delete this.mouseupfunc
    }, focus: function () {
        this.host.focus()
    }, destroy: function () {
        this._removeHandlers();
        var b = a.data(this.element, "jqxButton");
        if (b) {
            delete b.instance
        }
        this.host.removeClass();
        this.host.removeData();
        this.host.remove();
        delete this.set;
        delete this.get;
        delete this.call;
        delete this.propertyChangeMap.roundedCorners;
        delete this.propertyChangeMap.width;
        delete this.propertyChangeMap.height;
        delete this.propertyChangeMap.disabled;
        delete this.propertyChangeMap.rtl;
        delete this.propertyChangeMap.theme;
        delete this.propertyChangeMap;
        delete this.element;
        delete this.host
    }, render: function () {
        this.refresh()
    }, refresh: function () {
        if (this.overrideTheme) {
            return
        }
        var d = this.toThemeProperty("jqx-fill-state-focus");
        var h = this.toThemeProperty("jqx-fill-state-disabled");
        var b = this.toThemeProperty("jqx-fill-state-normal");
        if (!this.enableDefault) {
            b = ""
        }
        var g = this.toThemeProperty("jqx-fill-state-hover");
        var e = this.toThemeProperty("jqx-fill-state-pressed");
        var f = this.toThemeProperty("jqx-fill-state-pressed");
        if (!this.enablePressed) {
            e = ""
        }
        var c = "";
        if (!this.host) {
            return
        }
        this.host[0].disabled = this.disabled;
        if (this.disabled) {
            c = h
        } else {
            if (this.isMouseOver && !this.isTouchDevice) {
                if (this.isPressed) {
                    c = f
                } else {
                    c = g
                }
            } else {
                if (this.isPressed) {
                    c = e
                } else {
                    c = b
                }
            }
        }
        if (this.isFocused) {
            c += " " + d
        }
        if (c != this._oldCSSCurrent) {
            if (this._oldCSSCurrent) {
                this.host.removeClass(this._oldCSSCurrent)
            }
            this.host.addClass(c);
            this._oldCSSCurrent = c
        }
        if (this.rtl) {
            this.host.addClass(this.toThemeProperty("jqx-rtl"));
            this.host.css("direction", "rtl")
        }
    }});
    a.jqx.jqxWidget("jqxLinkButton", "", {});
    a.extend(a.jqx._jqxLinkButton.prototype, {defineInstance: function () {
        this.disabled = false;
        this.height = null;
        this.width = null;
        this.rtl = false;
        this.href = null
    }, createInstance: function (d) {
        var c = this;
        this.host.onselectstart = function () {
            return false
        };
        this.host.attr("role", "button");
        var b = this.height || this.host.height();
        var e = this.width || this.host.width();
        this.href = this.host.attr("href");
        this.target = this.host.attr("target");
        this.content = this.host.text();
        this.element.innerHTML = "";
        this.host.append("<input type='button' class='jqx-wrapper'/>");
        var f = this.host.find("input");
        f.addClass(this.toThemeProperty("jqx-reset"));
        f.width(e);
        f.height(b);
        f.val(this.content);
        this.host.find("tr").addClass(this.toThemeProperty("jqx-reset"));
        this.host.find("td").addClass(this.toThemeProperty("jqx-reset"));
        this.host.find("tbody").addClass(this.toThemeProperty("jqx-reset"));
        this.host.css("color", "inherit");
        this.host.addClass(this.toThemeProperty("jqx-link"));
        f.css({width: e});
        f.css({height: b});
        var g = d == undefined ? {} : d[0] || {};
        f.jqxButton(g);
        if (this.disabled) {
            this.host[0].disabled = true
        }
        this.propertyChangeMap.disabled = function (h, j, i, k) {
            h.host[0].disabled = k;
            h.host.find("input").jqxButton({disabled: k})
        };
        this.addHandler(f, "click", function (h) {
            if (!this.disabled) {
                c.onclick(h)
            }
            return false
        })
    }, onclick: function (b) {
        if (this.target != null) {
            window.open(this.href, this.target)
        } else {
            window.location = this.href
        }
    }});
    a.jqx.jqxWidget("jqxRepeatButton", "jqxButton", {});
    a.extend(a.jqx._jqxRepeatButton.prototype, {defineInstance: function () {
        this.delay = 50
    }, createInstance: function (e) {
        var c = this;
        var d = a.jqx.mobile.isTouchDevice();
        var b = !d ? "mouseup." + this.base.element.id : "touchend." + this.base.element.id;
        var f = !d ? "mousedown." + this.base.element.id : "touchstart." + this.base.element.id;
        this.addHandler(a(document), b, function (g) {
            if (c.timeout != null) {
                clearTimeout(c.timeout);
                c.timeout = null;
                c.refresh()
            }
            if (c.timer != undefined) {
                clearInterval(c.timer);
                c.timer = null;
                c.refresh()
            }
        });
        this.addHandler(this.base.host, f, function (g) {
            if (c.timer != null) {
                clearInterval(c.timer)
            }
            c.timeout = setTimeout(function () {
                clearInterval(c.timer);
                c.timer = setInterval(function (h) {
                    c.ontimer(h)
                }, c.delay)
            }, 150)
        });
        this.mousemovefunc = function (g) {
            if (!d) {
                if (g.which == 0) {
                    if (c.timer != null) {
                        clearInterval(c.timer);
                        c.timer = null
                    }
                }
            }
        };
        this.addHandler(this.base.host, "mousemove", this.mousemovefunc)
    }, destroy: function () {
        var c = a.jqx.mobile.isTouchDevice();
        var b = !c ? "mouseup." + this.base.element.id : "touchend." + this.base.element.id;
        var e = !c ? "mousedown." + this.base.element.id : "touchstart." + this.base.element.id;
        this.removeHandler(this.base.host, "mousemove", this.mousemovefunc);
        this.removeHandler(this.base.host, e);
        this.removeHandler(a(document), b);
        this.timer = null;
        delete this.mousemovefunc;
        delete this.timer;
        var d = a.data(this.base.element, "jqxRepeatButton");
        if (d) {
            delete d.instance
        }
        a(this.base.element).removeData();
        this.base.destroy();
        delete this.base
    }, stop: function () {
        clearInterval(this.timer);
        this.timer = null
    }, ontimer: function (b) {
        var b = new jQuery.Event("click");
        if (this.base != null && this.base.host != null) {
            this.base.host.trigger(b)
        }
    }});
    a.jqx.jqxWidget("jqxToggleButton", "jqxButton", {});
    a.extend(a.jqx._jqxToggleButton.prototype, {defineInstance: function () {
        this.toggled = false;
        this.aria = {"aria-checked": {name: "toggled", type: "boolean"}, "aria-disabled": {name: "disabled", type: "boolean"}}
    }, createInstance: function (c) {
        var b = this;
        this.base.overrideTheme = true;
        this.isTouchDevice = a.jqx.mobile.isTouchDevice();
        a.jqx.aria(this);
        this.propertyChangeMap.toggled = function (d, f, e, g) {
            d.refresh()
        };
        this.propertyChangeMap.disabled = function (d, f, e, g) {
            b.base.disabled = g;
            d.refresh()
        };
        this.addHandler(this.base.host, "click", function (d) {
            if (!b.base.disabled) {
                b.toggle()
            }
        });
        if (!this.isTouchDevice) {
            this.addHandler(this.base.host, "mouseenter", function (d) {
                if (!b.base.disabled) {
                    b.refresh()
                }
            });
            this.addHandler(this.base.host, "mouseleave", function (d) {
                if (!b.base.disabled) {
                    b.refresh()
                }
            })
        }
        this.addHandler(this.base.host, "mousedown", function (d) {
            if (!b.base.disabled) {
                b.refresh()
            }
        });
        this.addHandler(a(document), "mouseup", function (d) {
            if (!b.base.disabled) {
                b.refresh()
            }
        })
    }, _removeHandlers: function () {
        this.removeHandler(this.base.host, "click");
        this.removeHandler(this.base.host, "mouseenter");
        this.removeHandler(this.base.host, "mouseleave");
        this.removeHandler(this.base.host, "mousedown");
        this.removeHandler(a(document), "mouseup")
    }, toggle: function () {
        this.toggled = !this.toggled;
        this.refresh();
        a.jqx.aria(this, "aria-checked", this.toggled)
    }, unCheck: function () {
        this.toggled = false;
        this.refresh()
    }, check: function () {
        this.toggled = true;
        this.refresh()
    }, refresh: function () {
        var g = this.base.toThemeProperty("jqx-fill-state-disabled");
        var b = this.base.toThemeProperty("jqx-fill-state-normal");
        var f = this.base.toThemeProperty("jqx-fill-state-hover");
        var d = this.base.toThemeProperty("jqx-fill-state-pressed");
        var e = this.base.toThemeProperty("jqx-fill-state-pressed");
        var c = "";
        this.base.host[0].disabled = this.base.disabled;
        if (this.base.disabled) {
            c = g
        } else {
            if (this.base.isMouseOver && !this.isTouchDevice) {
                if (this.base.isPressed || this.toggled) {
                    c = e
                } else {
                    c = f
                }
            } else {
                if (this.base.isPressed || this.toggled) {
                    c = d
                } else {
                    c = b
                }
            }
        }
        if (this.base.host.hasClass(g) && g != c) {
            this.base.host.removeClass(g)
        }
        if (this.base.host.hasClass(b) && b != c) {
            this.base.host.removeClass(b)
        }
        if (this.base.host.hasClass(f) && f != c) {
            this.base.host.removeClass(f)
        }
        if (this.base.host.hasClass(d) && d != c) {
            this.base.host.removeClass(d)
        }
        if (this.base.host.hasClass(e) && e != c) {
            this.base.host.removeClass(e)
        }
        if (!this.base.host.hasClass(c)) {
            this.base.host.addClass(c)
        }
    }})
})(jQuery);