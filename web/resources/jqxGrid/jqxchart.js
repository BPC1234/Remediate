/*
 jQWidgets v3.2.0 (2014-Feb-01)
 Copyright (c) 2011-2014 jQWidgets.
 License: http://jqwidgets.com/license/
 */

(function (a) {
    a.jqx.jqxWidget("jqxChart", "", {});
    a.extend(a.jqx._jqxChart.prototype, {defineInstance: function () {
        this.title = "Title";
        this.description = "Description";
        this.source = [];
        this.seriesGroups = [];
        this.categoryAxis = {};
        this.renderEngine = undefined;
        this.enableAnimations = true;
        this.enableAxisTextAnimation = false;
        this.backgroundImage = this.background = undefined;
        this.padding = {left: 5, top: 5, right: 5, bottom: 5};
        this.backgroundColor = "#FFFFFF";
        this.showBorderLine = true;
        this.borderLineWidth = 1;
        this.titlePadding = {left: 5, top: 5, right: 5, bottom: 10};
        this.showLegend = true;
        this.legendLayout = undefined;
        this.enabled = true;
        this.colorScheme = "scheme01";
        this.animationDuration = 500;
        this.showToolTips = true;
        this.toolTipShowDelay = this.toolTipDelay = 500;
        this.toolTipHideDelay = 4000;
        this.toolTipFormatFunction = undefined;
        this.columnSeriesOverlap = false;
        this.rtl = false;
        this.legendPosition = null;
        this.borderLineColor = null;
        this.borderColor = null;
        this.greyScale = false;
        this.axisPadding = 5;
        this.enableCrosshairs = false;
        this.crosshairsColor = "#888888";
        this.crosshairsDashStyle = "2,2";
        this.crosshairsLineWidth = 1
    }, createInstance: function (e) {
        if (!a.jqx.dataAdapter) {
            throw"jqxdata.js is not loaded";
            return
        }
        this._refreshOnDownloadComlete();
        var c = this;
        this.host.on("mousemove", function (g) {
            if (c.enabled == false) {
                return
            }
            g.preventDefault();
            var f = g.pageX || g.clientX || g.screenX;
            var i = g.pageY || g.clientY || g.screenY;
            var h = c.host.offset();
            f -= h.left;
            i -= h.top;
            c.onmousemove(f, i)
        });
        this.addHandler(this.host, "mouseleave", function (f) {
            if (c.enabled == false) {
                return
            }
            if (c._plotRect && c._mouseX >= c._plotRect.x && c._mouseX <= c._plotRect.x + c._plotRect.width && c._mouseY >= c._plotRect.y && c._mouseY <= c._plotRect.y + c._plotRect.height) {
                return
            }
            c._cancelTooltipTimer();
            c._hideToolTip(0)
        });
        var d = a.jqx.mobile.isTouchDevice();
        this.addHandler(this.host, "click", function (g) {
            if (c.enabled == false) {
                return
            }
            if (!d) {
                c._cancelTooltipTimer();
                c._hideToolTip();
                c._unselect()
            }
            if (c._pointMarker && c._pointMarker.element) {
                var h = c.seriesGroups[c._pointMarker.gidx];
                var f = h.series[c._pointMarker.sidx];
                c._raiseItemEvent("click", h, f, c._pointMarker.iidx)
            }
        });
        if (this.element.style) {
            var b = false;
            if (this.element.style.width != null) {
                b |= this.element.style.width.toString().indexOf("%") != -1
            }
            if (this.element.style.height != null) {
                b |= this.element.style.height.toString().indexOf("%") != -1
            }
            if (b) {
                this.width = this.element.style.width;
                this.height = this.element.style.height;
                a.jqx.utilities.resize(this.host, function () {
                    if (c.timer) {
                        clearTimeout(c.timer)
                    }
                    var f = a.jqx.browser.msie ? 200 : 1;
                    c.timer = setTimeout(function () {
                        var g = c.enableAnimations;
                        c.enableAnimations = false;
                        c.refresh();
                        c.enableAnimations = g
                    }, f)
                })
            }
        }
    }, _refreshOnDownloadComlete: function () {
        if (this.source instanceof a.jqx.dataAdapter) {
            var c = this;
            var d = this.source._options;
            if (d == undefined || (d != undefined && !d.autoBind)) {
                this.source.autoSync = false;
                this.source.dataBind()
            }
            if (this.source.records.length == 0) {
                var b = function () {
                    if (c.ready) {
                        c.ready()
                    }
                    c.refresh()
                };
                this.source.unbindDownloadComplete(this.element.id);
                this.source.bindDownloadComplete(this.element.id, b)
            } else {
                if (c.ready) {
                    c.ready()
                }
            }
            this.source.unbindBindingUpdate(this.element.id);
            this.source.bindBindingUpdate(this.element.id, function () {
                c.refresh()
            })
        }
    }, propertyChangedHandler: function (b, c, e, d) {
        if (this.isInitialized == undefined || this.isInitialized == false) {
            return
        }
        if (c == "source") {
            this._refreshOnDownloadComlete()
        }
        this.refresh()
    }, _internalRefresh: function () {
        if (a.jqx.isHidden(this.host)) {
            return
        }
        this._stopAnimations();
        if (!this._isToggleRefresh && !this._isUpdate) {
            this.host.empty();
            this._toolTipElement = undefined;
            var c = null;
            if (document.createElementNS && (this.renderEngine == "SVG" || this.renderEngine == undefined)) {
                c = new a.jqx.svgRenderer();
                if (!c.init(this.host)) {
                    if (this.renderEngine == "SVG") {
                        throw"Your browser does not support SVG"
                    }
                    return
                }
            }
            if (c == null && this.renderEngine != "HTML5") {
                c = new a.jqx.vmlRenderer();
                if (!c.init(this.host)) {
                    if (this.renderEngine == "VML") {
                        throw"Your browser does not support VML"
                    }
                    return
                }
                this._isVML = true
            }
            if (c == null && (this.renderEngine == "HTML5" || this.renderEngine == undefined)) {
                c = new a.jqx.HTML5Renderer();
                if (!c.init(this.host)) {
                    throw"Your browser does not support HTML5 Canvas"
                }
            }
            this.renderer = c
        }
        var b = this.renderer.getRect();
        this._render({x: 1, y: 1, width: b.width, height: b.height});
        if (this.renderer instanceof a.jqx.HTML5Renderer) {
            this.renderer.refresh()
        }
        this._isUpdate = false
    }, saveAsPNG: function (c, b) {
        return this._saveAsImage("png", c, b)
    }, saveAsJPEG: function (c, b) {
        return this._saveAsImage("jpeg", c, b)
    }, _saveAsImage: function (j, g, l) {
        if (g == undefined || g == "") {
            g = "chart." + j
        }
        if (l == undefined || l == "") {
            l = "http://www.jqwidgets.com/export_server/export.php"
        }
        var k = this.renderEngine;
        var f = this.enableAnimations;
        this.enableAnimations = false;
        this.renderEngine = "HTML5";
        if (this.renderEngine != k) {
            try {
                this.refresh()
            } catch (i) {
                this.renderEngine = k;
                this.refresh();
                this.enableAnimations = f
            }
        }
        try {
            var d = this.renderer.getContainer()[0];
            if (d) {
                var h = d.toDataURL("image/" + j);
                h = h.replace("data:image/" + j + ";base64,", "");
                var c = document.createElement("form");
                c.method = "POST";
                c.action = l;
                c.style.display = "none";
                document.body.appendChild(c);
                var m = document.createElement("input");
                m.name = "fname";
                m.value = g;
                m.style.display = "none";
                var b = document.createElement("input");
                b.name = "content";
                b.value = h;
                b.style.display = "none";
                c.appendChild(m);
                c.appendChild(b);
                c.submit();
                document.body.removeChild(c)
            }
        } catch (i) {
        }
        if (this.renderEngine != k) {
            this.renderEngine = k;
            this.refresh();
            this.enableAnimations = f
        }
        return true
    }, refresh: function () {
        this._internalRefresh()
    }, update: function () {
        this._isUpdate = true;
        this._internalRefresh()
    }, _seriesTypes: ["line", "stackedline", "stackedline100", "spline", "stackedspline", "stackedspline100", "stepline", "stackedstepline", "stackedstepline100", "area", "stackedarea", "stackedarea100", "splinearea", "stackedsplinearea", "stackedsplinearea100", "steparea", "stackedsteparea", "stackedsteparea100", "rangearea", "splinerangearea", "steprangearea", "column", "stackedcolumn", "stackedcolumn100", "rangecolumn", "pie", "donut", "scatter", "bubble", "spider"], _render: function (v) {
        if (!this._isToggleRefresh && this._isUpdate && this._renderData) {
            this._renderDataDeepCopy()
        }
        this._renderData = [];
        this.renderer.clear();
        this._unselect();
        this._hideToolTip(0);
        var l = this.backgroundImage;
        if (l == undefined || l == "") {
            this.host.css({"background-image": ""})
        } else {
            this.host.css({"background-image": (l.indexOf("(") != -1 ? l : "url('" + l + "')")})
        }
        var P = this.padding || {left: 5, top: 5, right: 5, bottom: 5};
        var o = this.renderer.createClipRect(v);
        var D = this.renderer.beginGroup();
        this.renderer.setClip(D, o);
        var X = this.renderer.rect(v.x, v.y, v.width - 2, v.height - 2);
        if (l == undefined || l == "") {
            this.renderer.attr(X, {fill: this.background || this.backgroundColor || "white"})
        } else {
            this.renderer.attr(X, {fill: "transparent"})
        }
        if (this.showBorderLine != false) {
            var A = this.borderLineColor == undefined ? this.borderColor : this.borderLineColor;
            if (A == undefined) {
                A = "#888888"
            }
            var m = this.borderLineWidth;
            if (isNaN(m) || m < 0 || m > 10) {
                m = 1
            }
            this.renderer.attr(X, {"stroke-width": m, stroke: A})
        }
        var M = {x: P.left, y: P.top, width: v.width - P.left - P.right, height: v.height - P.top - P.bottom};
        this._paddedRect = M;
        var e = this.titlePadding || {left: 2, top: 2, right: 2, bottom: 2};
        if (this.title && this.title.length > 0) {
            var J = this.toThemeProperty("jqx-chart-title-text", null);
            var k = this.renderer.measureText(this.title, 0, {"class": J});
            this.renderer.text(this.title, M.x + e.left, M.y + e.top, M.width - (e.left + e.right), k.height, 0, {"class": J}, true, "center", "center");
            M.y += k.height;
            M.height -= k.height
        }
        if (this.description && this.description.length > 0) {
            var K = this.toThemeProperty("jqx-chart-title-description", null);
            var k = this.renderer.measureText(this.description, 0, {"class": K});
            this.renderer.text(this.description, M.x + e.left, M.y + e.top, M.width - (e.left + e.right), k.height, 0, {"class": K}, true, "center", "center");
            M.y += k.height;
            M.height -= k.height
        }
        if (this.title || this.description) {
            M.y += (e.bottom + e.top);
            M.height -= (e.bottom + e.top)
        }
        var b = {x: M.x, y: M.y, width: M.width, height: M.height};
        this._buildStats(b);
        var B = this._isPieOnlySeries();
        var s = {};
        for (var Q = 0; Q < this.seriesGroups.length && !B; Q++) {
            if (this.seriesGroups[Q].type == "pie" || this.seriesGroups[Q].type == "donut") {
                continue
            }
            var z = this.seriesGroups[Q].orientation == "horizontal";
            var t = this.seriesGroups[Q].valueAxis;
            if (!t) {
                throw"seriesGroup[" + Q + "] is missing " + (z ? "categoryAxis" : "valueAxis") + " definition"
            }
            var d = this._getCategoryAxis(Q);
            if (!d) {
                throw"seriesGroup[" + Q + "] is missing " + (!z ? "categoryAxis" : "valueAxis") + " definition"
            }
            var r = d == this.categoryAxis ? -1 : Q;
            s[r] = 0
        }
        var L = this.axisPadding;
        if (isNaN(L)) {
            L = 5
        }
        var p = {left: 0, right: 0, leftCount: 0, rightCount: 0};
        var n = [];
        for (var Q = 0; Q < this.seriesGroups.length; Q++) {
            var U = this.seriesGroups[Q];
            if (U.type == "pie" || U.type == "donut" || U.spider == true || U.polar == true) {
                n.push(0);
                continue
            }
            var z = U.orientation == "horizontal";
            var r = this._getCategoryAxis(Q) == this.categoryAxis ? -1 : Q;
            var I = t.axisSize;
            var f = {x: 0, y: b.y, width: b.width, height: b.height};
            var H = undefined;
            if (!I || I == "auto") {
                if (z) {
                    if (!this._isGroupVisible(Q)) {
                        continue
                    }
                    I = this._renderCategoryAxis(Q, f, true, b).width;
                    if ((s[r] & 1) == 1) {
                        I = 0
                    } else {
                        s[r] |= 1
                    }
                    H = this._getCategoryAxis(Q).position
                } else {
                    I = this._renderValueAxis(Q, f, true, b).width;
                    if (U.valueAxis) {
                        H = U.valueAxis.position
                    }
                }
            }
            if (H != "left" && this.rtl == true) {
                H = "right"
            }
            if (H != "right") {
                H = "left"
            }
            if (p[H + "Count"] > 0 && p[H] > 0 && I > 0) {
                p[H] += L
            }
            n.push({width: I, position: H, xRel: p[H]});
            p[H] += I;
            p[H + "Count"]++
        }
        var T = {top: 0, bottom: 0, topCount: 0, bottomCount: 0};
        var N = [];
        for (var Q = 0; Q < this.seriesGroups.length; Q++) {
            var U = this.seriesGroups[Q];
            if (U.type == "pie" || U.type == "donut" || U.spider == true || U.polar == true) {
                N.push(0);
                continue
            }
            var z = U.orientation == "horizontal";
            var d = this._getCategoryAxis(Q);
            var r = d == this.categoryAxis ? -1 : Q;
            H = undefined;
            var S = d.axisSize;
            if (!S || S == "auto") {
                if (z) {
                    S = this._renderValueAxis(Q, {x: 0, y: 0, width: 10000000, height: 0}, true, b).height;
                    if (this.seriesGroups[Q].valueAxis) {
                        H = U.valueAxis.position
                    }
                } else {
                    if (!this._isGroupVisible(Q)) {
                        N.push(0);
                        continue
                    }
                    S = this._renderCategoryAxis(Q, {x: 0, y: 0, width: 10000000, height: 0}, true).height;
                    if ((s[r] & 2) == 2) {
                        S = 0
                    } else {
                        s[r] |= 2
                    }
                    H = this._getCategoryAxis(Q).position
                }
            }
            if (H != "top") {
                H = "bottom"
            }
            if (T[H + "Count"] > 0 && T[H] > 0 && S > 0) {
                T[H] += L
            }
            N.push({height: S, position: H, yRel: T[H]});
            T[H] += S;
            T[H + "Count"]++
        }
        this._createAnimationGroup("series");
        this._plotRect = b;
        var q = (this.showLegend != false);
        var u = !q || this.legendLayout ? {width: 0, height: 0} : this._renderLegend(M, true);
        if (M.height < T.top + T.bottom + u.height || M.width < p.left + p.right) {
            return
        }
        b.height -= T.top + T.bottom + u.height;
        b.x += p.left;
        b.width -= p.left + p.right;
        b.y += T.top;
        if (!B) {
            var V = this.categoryAxis.tickMarksColor || "#888888";
            for (var Q = 0; Q < this.seriesGroups.length; Q++) {
                var U = this.seriesGroups[Q];
                if (U.polar == true || U.spider == true) {
                    continue
                }
                var z = U.orientation == "horizontal";
                var r = this._getCategoryAxis(Q) == this.categoryAxis ? -1 : Q;
                var f = {x: b.x, y: 0, width: b.width, height: N[Q].height};
                if (N[Q].position != "top") {
                    f.y = b.y + b.height + N[Q].yRel
                } else {
                    f.y = b.y - N[Q].yRel - N[Q].height
                }
                if (z) {
                    this._renderValueAxis(Q, f, false, b)
                } else {
                    if ((s[r] & 4) == 4) {
                        continue
                    }
                    if (!this._isGroupVisible(Q)) {
                        continue
                    }
                    this._renderCategoryAxis(Q, f, false, b);
                    s[r] |= 4
                }
            }
        }
        if (q) {
            var G = M.x + a.jqx._ptrnd((M.width - u.width) / 2);
            var F = b.y + b.height + T.bottom;
            var I = M.width;
            var S = u.height;
            if (this.legendLayout) {
                G = this.legendLayout.left || G;
                F = this.legendLayout.top || F;
                I = this.legendLayout.width || I;
                S = this.legendLayout.height || S
            }
            if (G + I > v.x + v.width) {
                I = v.x + v.width - G
            }
            if (F + S > v.y + v.height) {
                S = v.y + v.height - F
            }
            this._renderLegend({x: G, y: F, width: I, height: S})
        }
        this._hasHorizontalLines = false;
        if (!B) {
            for (var Q = 0; Q < this.seriesGroups.length; Q++) {
                var U = this.seriesGroups[Q];
                if (U.polar == true || U.spider == true) {
                    continue
                }
                var z = this.seriesGroups[Q].orientation == "horizontal";
                var f = {x: b.x - n[Q].xRel - n[Q].width, y: b.y, width: n[Q].width, height: b.height};
                if (n[Q].position != "left") {
                    f.x = b.x + b.width + n[Q].xRel
                }
                if (z) {
                    if ((s[this._getCategoryAxis(Q)] & 8) == 8) {
                        continue
                    }
                    if (!this._isGroupVisible(Q)) {
                        continue
                    }
                    this._renderCategoryAxis(Q, f, false, b);
                    s[this._getCategoryAxis(Q)] |= 8
                } else {
                    this._renderValueAxis(Q, f, false, b)
                }
            }
        }
        if (b.width <= 0 || b.height <= 0) {
            return
        }
        this._plotRect = {x: b.x, y: b.y, width: b.width, height: b.height};
        var C = this.renderer.createClipRect({x: b.x, y: b.y, width: b.width, height: b.height});
        var E = this.renderer.beginGroup();
        this.renderer.setClip(E, C);
        for (var Q = 0; Q < this.seriesGroups.length; Q++) {
            var U = this.seriesGroups[Q];
            var c = false;
            for (var W in this._seriesTypes) {
                if (this._seriesTypes[W] == U.type) {
                    c = true;
                    break
                }
            }
            if (!c) {
                throw'jqxChart: invalid series type "' + U.type + '"';
                continue
            }
            if (U.polar == true || U.spider == true) {
                if (U.type.indexOf("pie") == -1 && U.type.indexOf("donut") == -1) {
                    this._renderSpiderAxis(Q, b)
                }
            }
            if (U.bands) {
                for (var O = 0; O < U.bands.length; O++) {
                    this._renderBand(Q, O, b)
                }
            }
            if (U.type.indexOf("column") != -1) {
                this._renderColumnSeries(Q, b)
            } else {
                if (U.type.indexOf("pie") != -1 || U.type.indexOf("donut") != -1) {
                    this._renderPieSeries(Q, b)
                } else {
                    if (U.type.indexOf("line") != -1 || U.type.indexOf("area") != -1) {
                        this._renderLineSeries(Q, b)
                    } else {
                        if (U.type == "scatter" || U.type == "bubble") {
                            this._renderScatterSeries(Q, b)
                        }
                    }
                }
            }
        }
        this.renderer.endGroup();
        if (this.enabled == false) {
            var R = this.renderer.rect(v.x, v.y, v.width, v.height);
            this.renderer.attr(R, {fill: "#777777", opacity: 0.5, stroke: "#00FFFFFF"})
        }
        this.renderer.endGroup();
        this._startAnimation("series")
    }, _isPieOnlySeries: function () {
        if (this.seriesGroups.length == 0) {
            return false
        }
        for (var b = 0; b < this.seriesGroups.length; b++) {
            if (this.seriesGroups[b].type != "pie" && this.seriesGroups[b].type != "donut") {
                return false
            }
        }
        return true
    }, _renderChartLegend: function (E, c, d, h) {
        var q = {x: c.x + 3, y: c.y + 3, width: c.width - 6, height: c.height - 6};
        var m = {width: q.width, height: 0};
        var l = 0, k = 0;
        var j = 20;
        var b = 0;
        var u = 10;
        var C = 10;
        var A = 0;
        for (var w = 0; w < E.length; w++) {
            var n = E[w].css;
            if (!n) {
                n = this.toThemeProperty("jqx-chart-legend-text", null)
            }
            var o = E[w].text;
            var p = this.renderer.measureText(o, 0, {"class": n});
            if (p.height > j) {
                j = p.height
            }
            if (p.width > A) {
                A = p.width
            }
            if (h) {
                if (w != 0) {
                    k += j
                }
                if (k > q.height) {
                    k = 0;
                    l += A + C;
                    A = p.width;
                    m.width = l + A
                }
            } else {
                if (l != 0) {
                    l += C
                }
                if (l + 2 * u + p.width > q.width && p.width < q.width) {
                    l = 0;
                    k += j;
                    j = 20;
                    b = q.width;
                    m.heigh = k + j
                }
            }
            if (!d && q.x + l + p.width < c.x + c.width && q.y + k + p.height < c.y + c.height) {
                var t = E[w].seriesIndex;
                var D = E[w].groupIndex;
                var f = E[w].itemIndex;
                var v = E[w].color;
                var s = this._isSerieVisible(D, t, f);
                var z = this.renderer.beginGroup();
                var B = this.renderer.rect(q.x + l, q.y + k + u / 2, u, u);
                var e = s ? E[w].opacity : 0.1;
                this.renderer.attr(B, {fill: v, "fill-opacity": e, stroke: v, "stroke-width": 1, "stroke-opacity": E[w].opacity});
                this.renderer.text(o, q.x + l + 1.5 * u, q.y + k, p.width, j, 0, {"class": n}, false, "center", "center");
                this.renderer.endGroup();
                this._setLegendToggleHandler(D, t, f, z)
            }
            if (h) {
            } else {
                l += p.width + 2 * u;
                if (b < l) {
                    b = l
                }
            }
        }
        if (d) {
            m.height = a.jqx._ptrnd(k + j + 5);
            m.width = a.jqx._ptrnd(b);
            return m
        }
    }, _groupSeriesToggleState: [], _isSerieVisible: function (f, b, d) {
        while (this._groupSeriesToggleState.length < f + 1) {
            this._groupSeriesToggleState.push([])
        }
        var e = this._groupSeriesToggleState[f];
        while (e.length < b + 1) {
            e.push(isNaN(d) ? true : [])
        }
        var c = e[b];
        if (isNaN(d)) {
            return c
        }
        if (!a.isArray(c)) {
            e[b] = c = []
        }
        while (c.length < d + 1) {
            c.push(true)
        }
        return c[d]
    }, _isGroupVisible: function (e) {
        var d = false;
        var c = this.seriesGroups[e].series;
        if (!c) {
            return szMeasure
        }
        for (var b = 0; b < c.length; b++) {
            if (this._isSerieVisible(e, b)) {
                d = true;
                break
            }
        }
        return d
    }, _isToggleRefresh: false, _toggleSerie: function (h, b, e, c) {
        var g = !this._isSerieVisible(h, b, e);
        if (c != undefined) {
            g = c
        }
        var i = this.seriesGroups[h];
        var f = i.series[b];
        this._raiseEvent("toggle", {state: g, seriesGroup: i, serie: f, elementIndex: e});
        if (isNaN(e)) {
            this._groupSeriesToggleState[h][b] = g
        } else {
            var d = this._groupSeriesToggleState[h][b];
            if (!a.isArray(d)) {
                d = []
            }
            while (d.length < e) {
                d.push(true)
            }
            d[e] = g
        }
        this._isToggleRefresh = true;
        this.update();
        this._isToggleRefresh = false
    }, showSerie: function (d, b, c) {
        this._toggleSerie(d, b, c, true)
    }, hideSerie: function (d, b, c) {
        this._toggleSerie(d, b, c, false)
    }, _setLegendToggleHandler: function (j, c, h, e) {
        var i = this.seriesGroups[j];
        var f = i.series[c];
        var b = f.enableSeriesToggle;
        if (b == undefined) {
            b = i.enableSeriesToggle != false
        }
        if (b) {
            var d = this;
            this.renderer.addHandler(e, "click", function (g) {
                g.preventDefault();
                d._toggleSerie(j, c, h)
            })
        }
    }, _renderLegend: function (o, n) {
        var d = [];
        for (var r = 0; r < this.seriesGroups.length; r++) {
            var l = this.seriesGroups[r];
            if (l.showLegend == false) {
                continue
            }
            for (var p = 0; p < l.series.length; p++) {
                var t = l.series[p];
                if (t.showLegend == false) {
                    continue
                }
                var f = this._getSerieSettings(r, p);
                if (l.type == "pie" || l.type == "donut") {
                    var j = this._getCategoryAxis(r);
                    var k = j.toolTipFormatSettings || j.formatSettings || l.toolTipFormatSettings || l.formatSettings || t.toolTipFormatSettings || t.formatSettings;
                    var c = j.toolTipFormatFunction || j.formatFunction || l.toolTipFormatFunction || l.formatFunction || t.toolTipFormatFunction || t.formatFunction;
                    var e = this._getDataLen(r);
                    for (var h = 0; h < e; h++) {
                        var m = this._getDataValue(h, t.displayText, r);
                        m = this._formatValue(m, k, c);
                        var b = this._getColors(r, p, h);
                        d.push({groupIndex: r, seriesIndex: p, itemIndex: h, text: m, css: t.displayTextClass, color: b.fillColor, opacity: f.opacity})
                    }
                    continue
                }
                var q = t.displayText || t.dataField || "";
                var b = this._getSeriesColors(r, p);
                d.push({groupIndex: r, seriesIndex: p, text: q, css: t.displayTextClass, color: b.fillColor, opacity: f.opacity})
            }
        }
        return this._renderChartLegend(d, o, n, (this.legendLayout && this.legendLayout.flow == "vertical"))
    }, _renderCategoryAxis: function (e, w, L, d) {
        var r = this._getCategoryAxis(e);
        var K = this.seriesGroups[e];
        var P = K.orientation == "horizontal";
        var G = {width: 0, height: 0};
        if (!r || r.visible == false || K.type == "spider") {
            return G
        }
        if (!this._isGroupVisible(e)) {
            return G
        }
        if (this.rtl) {
            r.flip = true
        }
        var u = r.text;
        var f = {visible: (r.showGridLines != false), color: (r.gridLinesColor || "#888888"), unitInterval: (r.gridLinesInterval || r.unitInterval), dashStyle: r.gridLinesDashStyle};
        var z = {visible: (r.showTickMarks != false), color: (r.tickMarksColor || "#888888"), unitInterval: (r.tickMarksInterval || r.unitInterval), dashStyle: r.tickMarksDashStyle};
        var p = r.textRotationAngle || 0;
        var s = this._calculateXOffsets(e, P ? w.height : w.width);
        var F = r.unitInterval;
        if (isNaN(F)) {
            F = 1
        }
        var c = r.horizontalTextAlignment;
        var O = this._alignValuesWithTicks(e);
        var l = this.renderer.getRect();
        var k = l.width - w.x - w.width;
        var o = this._getDataLen(e);
        var m;
        if (this._elementRenderInfo && this._elementRenderInfo.length > e) {
            m = this._elementRenderInfo[e].categoryAxis
        }
        var q = [];
        if (r.type != "date") {
            var E = s.customRange != false;
            var B = F;
            for (var J = s.min; J <= s.max; J += B) {
                if (E || r.dataField == undefined || r.dataField == "") {
                    H = J
                } else {
                    var N = Math.round(J);
                    H = this._getDataValue(N, r.dataField)
                }
                var u = this._formatValue(H, r.formatSettings, r.formatFunction, undefined, undefined, N);
                if (u == undefined) {
                    u = !E ? H.toString() : (J).toString()
                }
                var b = {key: H, text: u};
                if (m && m.itemOffsets[H]) {
                    b.x = m.itemOffsets[H].x;
                    b.y = m.itemOffsets[H].y
                }
                q.push(b);
                if (J + B > s.max) {
                    B = s.max - J;
                    if (B <= F / 2) {
                        break
                    }
                }
            }
        } else {
            var n = this._getDateTimeArray(s.min, s.max, r.baseUnit, O, F);
            for (var J = 0; J < n.length; J += F) {
                var H = n[J];
                var u = this._formatValue(H, r.formatSettings, r.formatFunction);
                var b = {key: H, text: u};
                if (m && m.itemOffsets[H]) {
                    b.x = m.itemOffsets[H].x;
                    b.y = m.itemOffsets[H].y
                }
                q.push(b)
            }
        }
        if (r.flip == true || this.rtl) {
            q.reverse()
        }
        var I = r.descriptionClass;
        if (!I) {
            I = this.toThemeProperty("jqx-chart-axis-description", null)
        }
        var v = r["class"];
        if (!v) {
            v = this.toThemeProperty("jqx-chart-axis-text", null)
        }
        if (P) {
            p -= 90
        }
        var M = {text: r.description, style: I, halign: r.horizontalDescriptionAlignment || "center", valign: r.verticalDescriptionAlignment || "center", textRotationAngle: P ? -90 : 0};
        var h = {textRotationAngle: p, style: v, halign: c, valign: r.verticalTextAlignment || "center", textRotationPoint: r.textRotationPoint || "auto", textOffset: r.textOffset};
        var D = (P && r.position == "right") || (!P && r.position == "top");
        var j = {rangeLength: s.rangeLength, itemWidth: s.itemWidth, intervalWidth: s.intervalWidth};
        var A = {items: q, renderData: j};
        while (this._renderData.length < e + 1) {
            this._renderData.push({})
        }
        this._renderData[e].categoryAxis = j;
        var C = this._getAnimProps(e);
        var t = C.enabled && q.length < 500 ? C.duration : 0;
        if (this.enableAxisTextAnimation == false) {
            t = 0
        }
        return this._renderAxis(P, D, M, h, {x: w.x, y: w.y, width: w.width, height: w.height}, d, F, false, O, A, f, z, L, t)
    }, _animateAxisText: function (f, h) {
        var c = f.items;
        var d = f.textSettings;
        for (var e = 0; e < c.length; e++) {
            var g = c[e];
            if (!g.visible) {
                continue
            }
            var b = g.targetX;
            var j = g.targetY;
            if (!isNaN(g.x) && !isNaN(g.y)) {
                b = g.x + (b - g.x) * h;
                j = g.y + (j - g.y) * h
            } else {
                if (h != 1) {
                }
            }
            if (g.element) {
                this.renderer.removeElement(g.element);
                g.element = undefined
            }
            g.element = this.renderer.text(g.text, b, j, g.width, g.height, d.textRotationAngle, {"class": d.style}, false, d.halign, d.valign, d.textRotationPoint)
        }
    }, _getPolarAxisCoords: function (j, h) {
        var i = this.seriesGroups[j];
        var c = this._calcGroupOffsets(j, h).xoffsets;
        if (!c) {
            return
        }
        var e = h.x + a.jqx.getNum([i.offsetX, h.width / 2]);
        var d = h.y + a.jqx.getNum([i.offsetY, h.height / 2]);
        var f = i.radius;
        if (isNaN(f)) {
            f = Math.min(h.width, h.height) / 2 * 0.7
        }
        var b = this._alignValuesWithTicks(j);
        var g = i.startAngle;
        if (isNaN(g)) {
            g = 0
        } else {
            g = (g < 0 ? -1 : 1) * (Math.abs(g) % 360);
            g = 2 * Math.PI * g / 360
        }
        return{x: e, y: d, r: f, itemWidth: c.itemWidth, rangeLength: c.rangeLength, valuesOnTicks: b, startAngle: g}
    }, _toPolarCoord: function (d, g, c, i) {
        var h = ((c - g.x) * 2 * Math.PI) / Math.max(1, g.width) + d.startAngle;
        var b = ((g.height + g.y) - i) * d.r / Math.max(1, g.height);
        var f = d.x + b * Math.cos(h);
        var e = d.y + b * Math.sin(h);
        return{x: a.jqx._ptrnd(f), y: a.jqx._ptrnd(e)}
    }, _renderSpiderAxis: function (e, G) {
        var B = this._getCategoryAxis(e);
        if (!B || B.visible == false) {
            return
        }
        var v = this.seriesGroups[e];
        var H = this._getPolarAxisCoords(e, G);
        if (!H) {
            return
        }
        var Q = a.jqx._ptrnd(H.x);
        var O = a.jqx._ptrnd(H.y);
        var T = H.r;
        var h = H.startAngle;
        if (T < 1) {
            return
        }
        T = a.jqx._ptrnd(T);
        var C = this._calculateXOffsets(e, Math.PI * 2 * T);
        if (!C.rangeLength) {
            return
        }
        var N = B.unitInterval;
        if (isNaN(N) || N < 1) {
            N = 1
        }
        var d = B.horizontalTextAlignment;
        var Y = this._alignValuesWithTicks(e);
        var p = this.renderer.getRect();
        var n = p.width - G.x - G.width;
        var u = this._getDataLen(e);
        var r;
        if (this._elementRenderInfo && this._elementRenderInfo.length > e) {
            r = this._elementRenderInfo[e].categoryAxis
        }
        var z = [];
        if (B.type != "date") {
            var M = C.customRange != false;
            var K = N;
            for (var V = C.min; V <= C.max; V += K) {
                if (M || B.dataField == undefined || B.dataField == "") {
                    R = V
                } else {
                    var X = Math.round(V);
                    R = this._getDataValue(X, B.dataField)
                }
                var E = this._formatValue(R, B.formatSettings, B.formatFunction, undefined, undefined, X);
                if (E == undefined) {
                    E = !M ? R.toString() : (V).toString()
                }
                var c = {key: R, text: E};
                if (r && r.itemOffsets[R]) {
                    c.x = r.itemOffsets[R].x;
                    c.y = r.itemOffsets[R].y
                }
                z.push(c);
                if (V + K > C.max) {
                    K = C.max - V;
                    if (K <= N / 2) {
                        break
                    }
                }
            }
        } else {
            var s = this._getDateTimeArray(C.min, C.max, B.baseUnit, Y, N);
            for (var V = 0; V < s.length; V += N) {
                var R = s[V];
                var E = this._formatValue(R, B.formatSettings, B.formatFunction);
                var c = {key: R, text: E};
                if (r && r.itemOffsets[R]) {
                    c.x = r.itemOffsets[R].x;
                    c.y = r.itemOffsets[R].y
                }
                z.push(c)
            }
        }
        if (B.flip == true || this.rtl) {
            z.reverse()
        }
        var S = B.descriptionClass;
        if (!S) {
            S = this.toThemeProperty("jqx-chart-axis-description", null)
        }
        var F = B["class"];
        if (!F) {
            F = this.toThemeProperty("jqx-chart-axis-text", null)
        }
        var E = B.text;
        var w = B.textRotationAngle || 0;
        var Z = this.seriesGroups[e].orientation == "horizontal";
        if (Z) {
            w -= 90
        }
        var f = {visible: (B.showGridLines != false), color: (B.gridLinesColor || "#888888"), unitInterval: (B.gridLinesInterval || B.unitInterval), dashStyle: B.gridLinesDashStyle};
        var I = {visible: (B.showTickMarks != false), color: (B.tickMarksColor || "#888888"), unitInterval: (B.tickMarksInterval || B.unitInterval), dashStyle: B.tickMarksDashStyle};
        var W = {text: B.description, style: S, halign: B.horizontalDescriptionAlignment || "center", valign: B.verticalDescriptionAlignment || "center", textRotationAngle: Z ? -90 : 0};
        var k = {textRotationAngle: w, style: F, halign: d, valign: B.verticalTextAlignment || "center", textRotationPoint: B.textRotationPoint || "auto", textOffset: B.textOffset};
        var L = (Z && B.position == "right") || (!Z && B.position == "top");
        var m = {rangeLength: C.rangeLength, itemWidth: C.itemWidth};
        var J = {items: z, renderData: m};
        while (this._renderData.length < e + 1) {
            this._renderData.push({})
        }
        this._renderData[e].categoryAxis = m;
        var l = {stroke: f.color, fill: "none", "stroke-width": 1, "stroke-dasharray": f.dashStyle || ""};
        var U = this.renderer.circle(Q, O, T, l);
        var A = z.length;
        var j = 2 * Math.PI / (A);
        var b = (Y ? 0 : j / 2) + h;
        for (var V = 0; V < A; V++) {
            var t = j * V + b;
            var g = this.renderer.measureText(z[V].text, 0, {"class": F});
            var D = Math.sqrt(g.width * g.width + g.height * g.height) / 2;
            x = Q + (T + 5 + D) * Math.cos(t) - g.width / 2;
            y = O + (T + 5 + D) * Math.sin(t) - g.height / 2;
            this.renderer.text(z[V].text, x, y, g.width, g.height, 0, {"class": F}, false, "center", "center")
        }
        A = C.rangeLength;
        j = 2 * Math.PI / (A);
        b = h;
        if (f.visible) {
            var o = f.unitInterval;
            if (isNaN(o) || o < 1) {
                o = N
            }
            for (var V = 0; V < A; V += o) {
                t = j * V + b;
                x = Q + T * Math.cos(t);
                y = O + T * Math.sin(t);
                this.renderer.line(Q, O, a.jqx._ptrnd(x), a.jqx._ptrnd(y), l)
            }
        }
        if (I.visible) {
            var q = 5;
            var P = I.unitInterval;
            if (isNaN(P) || P < 1) {
                P = N
            }
            var l = {stroke: I.color, fill: "none", "stroke-width": 1, "stroke-dasharray": I.dashStyle || ""};
            for (var V = 0; V < A; V += P) {
                var t = j * V + b;
                x1 = Q + T * Math.cos(t);
                y1 = O + T * Math.sin(t);
                x2 = Q + (T + q) * Math.cos(t);
                y2 = O + (T + q) * Math.sin(t);
                this.renderer.line(a.jqx._ptrnd(x1), a.jqx._ptrnd(y1), a.jqx._ptrnd(x2), a.jqx._ptrnd(y2), l)
            }
        }
        this._renderSpiderValueAxis(e, G)
    }, _renderSpiderValueAxis: function (e, c) {
        var j = this.seriesGroups[e];
        var t = this._getPolarAxisCoords(e, c);
        if (!t) {
            return
        }
        var G = a.jqx._ptrnd(t.x);
        var F = a.jqx._ptrnd(t.y);
        var g = t.r;
        var z = t.startAngle;
        if (g < 1) {
            return
        }
        g = a.jqx._ptrnd(g);
        var D = this.seriesGroups[e].valueAxis;
        if (!D || false == D.displayValueAxis) {
            return
        }
        var n = D["class"];
        if (!n) {
            n = this.toThemeProperty("jqx-chart-axis-text", null)
        }
        var m = D.formatSettings;
        var d = j.type.indexOf("stacked") != -1 && j.type.indexOf("100") != -1;
        if (d && !m) {
            m = {sufix: "%"}
        }
        this._calcValueAxisItems(e, g);
        var h = this._stats.seriesGroups[e].mu;
        var f = {visible: (D.showGridLines != false), color: (D.gridLinesColor || "#888888"), unitInterval: (D.gridLinesInterval || h || 1), dashStyle: D.gridLinesDashStyle};
        var b = {stroke: f.color, fill: "none", "stroke-width": 1, "stroke-dasharray": f.dashStyle || ""};
        var p = this._renderData[e].valueAxis;
        var s = p.items;
        if (s.length) {
            this.renderer.line(G, F, G, a.jqx._ptrnd(F - g), b)
        }
        s = s.reverse();
        var v = this.renderer;
        for (var A = 0; A < s.length - 1; A++) {
            var u = s[A];
            var o = (D.formatFunction) ? D.formatFunction(u) : this._formatNumber(u, m);
            var q = v.measureText(o, 0, {"class": n});
            var l = G + (D.showTickMarks != false ? 3 : 2);
            var k = F - p.itemWidth * A - q.height;
            v.text(o, l, k, q.width, q.height, 0, {"class": n}, false, "center", "center")
        }
        var r = D.logarithmicScale == true;
        var C = r ? s.length : p.rangeLength;
        aIncrement = 2 * Math.PI / C;
        if (f.visible) {
            var b = {stroke: f.color, fill: "none", "stroke-width": 1, "stroke-dasharray": f.dashStyle || ""};
            for (var A = 0; A < C; A += f.unitInterval) {
                var k = a.jqx._ptrnd(g * A / C);
                v.circle(G, F, k, b)
            }
        }
        var w = {visible: (D.showTickMarks != false), color: (D.tickMarksColor || "#888888"), unitInterval: (D.tickMarksInterval || h), dashStyle: D.tickMarksDashStyle};
        if (w.visible) {
            tickMarkSize = 5;
            var b = {stroke: w.color, fill: "none", "stroke-width": 1, "stroke-dasharray": w.dashStyle || ""};
            var E = G - Math.round(tickMarkSize / 2);
            var B = E + tickMarkSize;
            for (var A = 0; A < C; A += w.unitInterval) {
                if (f.visible && (A % f.unitInterval) == 0) {
                    continue
                }
                var k = a.jqx._ptrnd(F - g * A / C);
                v.line(a.jqx._ptrnd(E), k, a.jqx._ptrnd(B), k, b)
            }
        }
    }, _renderAxis: function (N, J, Z, q, D, c, L, p, ab, H, e, E, Y, d) {
        var r = E.visible ? 4 : 0;
        var V = 2;
        var M = {width: 0, height: 0};
        var u = {width: 0, height: 0};
        if (N) {
            M.height = u.height = D.height
        } else {
            M.width = u.width = D.width
        }
        if (!Y && J) {
            if (N) {
                D.x -= D.width
            }
        }
        var n = H.renderData;
        var b = n.intervalWidth;
        var aa = n.rangeLength;
        if (Z.text != undefined && Z != "") {
            var v = Z.textRotationAngle;
            var g = this.renderer.measureText(Z.text, v, {"class": Z.style});
            u.width = g.width;
            u.height = g.height;
            if (!Y) {
                this.renderer.text(Z.text, D.x + (N ? (!J ? V : -V + 2 * D.width - u.width) : 0), D.y + (!N ? (!J ? D.height - V - u.height : V) : 0), N ? u.width : D.width, !N ? u.height : D.height, v, {"class": Z.style}, true, Z.halign, Z.valign)
            }
        }
        var S = 0;
        var B = ab ? -b / 2 : 0;
        if (ab && !N) {
            q.halign = "center"
        }
        var U = D.x;
        var T = D.y;
        var K = q.textOffset;
        if (K) {
            if (!isNaN(K.x)) {
                U += K.x
            }
            if (!isNaN(K.y)) {
                T += K.y
            }
        }
        if (!N) {
            U += B;
            if (J) {
                T += u.height > 0 ? u.height + 3 * V : 2 * V;
                T += r - (ab ? r : r / 4)
            } else {
                T += ab ? r : r / 4
            }
        } else {
            U += V + (u.width > 0 ? (u.width + V) : 0) + (J ? D.width - u.width : 0);
            T += B
        }
        var X = 0;
        var R = 0;
        var z = H.items;
        n.itemOffsets = {};
        if (this._isToggleRefresh || !this._isUpdate) {
            d = 0
        }
        var m = false;
        for (var W = 0; W < z.length; W++, S += b) {
            var C = z[W].text;
            var g = this.renderer.measureText(C, q.textRotationAngle, {"class": q.style});
            if (g.width > R) {
                R = g.width
            }
            if (g.height > X) {
                X = g.height
            }
            if (!Y) {
                if ((N && S > D.height + 2) || (!N && S > D.width + 2)) {
                    break
                }
                var P = N ? U : U + S;
                var O = N ? T + S : T;
                n.itemOffsets[z[W].key] = {x: P, y: O};
                if (!m) {
                    if (!isNaN(z[W].x) || !isNaN(z[W].y) && d) {
                        m = true
                    }
                }
                z[W].targetX = P;
                z[W].targetY = O;
                z[W].width = !N ? b : D.width - 2 * V - r - ((u.width > 0) ? u.width + V : 0);
                z[W].height = N ? b : D.height - 2 * V - r - ((u.height > 0) ? u.height + V : 0);
                z[W].visible = !p || (p && (W % L) == 0)
            }
        }
        if (!Y) {
            var A = {items: z, textSettings: q};
            if (isNaN(d) || !m) {
                d = 0
            }
            this._animateAxisText(A, d == 0 ? 1 : 0);
            var j = this;
            this._enqueueAnimation("series", undefined, undefined, d, function (i, h, w) {
                j._animateAxisText(h, w)
            }, A)
        }
        M.width += 2 * V + r + u.width + R + (N && u.width > 0 ? V : 0);
        M.height += 2 * V + r + u.height + X + (!N && u.height > 0 ? V : 0);
        var G = {};
        var l = {stroke: e.color, "stroke-width": 1, "stroke-dasharray": e.dashStyle || ""};
        if (!Y) {
            var O = a.jqx._ptrnd(D.y + (J ? D.height : 0));
            if (N) {
                this.renderer.line(a.jqx._ptrnd(D.x + D.width), D.y, a.jqx._ptrnd(D.x + D.width), D.y + D.height, l)
            } else {
                this.renderer.line(a.jqx._ptrnd(D.x), O, a.jqx._ptrnd(D.x + D.width + 1), O, l)
            }
        }
        var t = 0.5;
        if (!Y && e.visible != false) {
            var o = e.unitInterval;
            if (isNaN(o) || o <= 0) {
                o = L
            }
            var s = p ? z.length : aa;
            var F = p ? 1 : o;
            var I = p ? b : (N ? D.height : D.width) / aa;
            var W = 0;
            while (W <= s) {
                if (p && a.jqx._mod(W, o) != 0) {
                    W += F;
                    continue
                }
                var k = 0;
                if (N) {
                    k = a.jqx._ptrnd(D.y + W * I);
                    if (k > D.y + D.height + t) {
                        break
                    }
                } else {
                    k = a.jqx._ptrnd(D.x + W * I);
                    if (k > D.x + D.width + t) {
                        break
                    }
                }
                if (N) {
                    this.renderer.line(a.jqx._ptrnd(c.x), k, a.jqx._ptrnd(c.x + c.width), k, l)
                } else {
                    this.renderer.line(k, a.jqx._ptrnd(c.y), k, a.jqx._ptrnd(c.y + c.height), l)
                }
                G[k] = true;
                W += F;
                if (W > s && W != s + F) {
                    W = s
                }
            }
        }
        var l = {stroke: E.color, "stroke-width": 1, "stroke-dasharray": E.dashStyle || ""};
        if (!Y && E.visible) {
            var Q = E.unitInterval;
            if (isNaN(Q) || Q <= 0) {
                Q = L
            }
            var s = p ? z.length : aa + Q;
            var F = p ? 1 : Q;
            var I = p ? b : (N ? D.height : D.width) / aa;
            for (var W = 0; W <= s; W += F) {
                if (p && a.jqx._mod(W, Q / L) != 0) {
                    continue
                }
                var k = a.jqx._ptrnd((N ? D.y : D.x) + W * I);
                if (G[k - 1]) {
                    k--
                } else {
                    if (G[k + 1]) {
                        k++
                    }
                }
                if (N) {
                    if (k > D.y + D.height + t) {
                        break
                    }
                } else {
                    if (k > D.x + D.width + t) {
                        break
                    }
                }
                var f = !J ? -r : r;
                if (N) {
                    this.renderer.line(D.x + D.width, k, D.x + D.width + f, k, l)
                } else {
                    var O = a.jqx._ptrnd(D.y + (J ? D.height : 0));
                    this.renderer.line(k, O, k, O - f, l)
                }
            }
        }
        M.width = a.jqx._rup(M.width);
        M.height = a.jqx._rup(M.height);
        return M
    }, _calcValueAxisItems: function (j, d) {
        var m = this._stats.seriesGroups[j];
        if (!m || !m.isValid) {
            return false
        }
        var v = this.seriesGroups[j];
        var b = v.orientation == "horizontal";
        var f = v.valueAxis;
        var l = f.valuesOnTicks != false;
        var e = f.dataField;
        var n = m.intervals;
        var r = d / n;
        var t = m.min;
        var q = m.mu;
        var c = f.logarithmicScale == true;
        var k = f.logarithmicScaleBase || 10;
        var h = v.type.indexOf("stacked") != -1 && v.type.indexOf("100") != -1;
        if (c) {
            q = !isNaN(f.unitInterval) ? f.unitInterval : 1
        }
        if (!l) {
            n = Math.max(n - 1, 1)
        }
        while (this._renderData.length < j + 1) {
            this._renderData.push({})
        }
        this._renderData[j].valueAxis = {};
        var o = this._renderData[j].valueAxis;
        o.itemWidth = o.intervalWidth = r;
        o.items = [];
        var p = o.items;
        for (var u = 0; u <= n; u++) {
            var s = 0;
            if (c) {
                if (h) {
                    s = m.max / Math.pow(k, n - u)
                } else {
                    s = t * Math.pow(k, u)
                }
            } else {
                s = l ? t + u * q : t + (u + 0.5) * q
            }
            p.push(s)
        }
        o.rangeLength = c && !h ? m.intervals : (m.intervals) * q;
        if (v.valueAxis.flip != true) {
            p = p.reverse()
        }
        return true
    }, _renderValueAxis: function (o, h, l, p) {
        var K = this.seriesGroups[o];
        var e = K.orientation == "horizontal";
        var j = K.valueAxis;
        if (!j) {
            throw"SeriesGroup " + o + " is missing valueAxis definition"
        }
        var w = {width: 0, height: 0};
        if (!this._isGroupVisible(o) || this._isPieOnlySeries() || K.type == "spider") {
            return w
        }
        if (!this._calcValueAxisItems(o, (e ? h.width : h.height)) || false == j.displayValueAxis || false == j.visible) {
            return w
        }
        var q = j.descriptionClass;
        if (!q) {
            q = this.toThemeProperty("jqx-chart-axis-description", null)
        }
        var v = {text: j.description, style: q, halign: j.horizontalDescriptionAlignment || "center", valign: j.verticalDescriptionAlignment || "center", textRotationAngle: e ? 0 : (!this.rtl ? -90 : 90)};
        var z = j.itemsClass;
        if (!z) {
            z = this.toThemeProperty("jqx-chart-axis-text", null)
        }
        var d = {style: z, halign: j.horizontalTextAlignment || "center", valign: j.verticalTextAlignment || "center", textRotationAngle: j.textRotationAngle || 0, textRotationPoint: j.textRotationPoint || "auto", textOffset: j.textOffset};
        var r = j.valuesOnTicks != false;
        var u = this._stats.seriesGroups[o];
        var E = u.mu;
        var J = j.formatSettings;
        var k = K.type.indexOf("stacked") != -1 && K.type.indexOf("100") != -1;
        if (k && !J) {
            J = {sufix: "%"}
        }
        var f = j.logarithmicScale == true;
        var n = j.logarithmicScaleBase || 10;
        if (f) {
            E = !isNaN(j.unitInterval) ? j.unitInterval : 1
        }
        var D = [];
        var C = this._renderData[o].valueAxis;
        var m;
        if (this._elementRenderInfo && this._elementRenderInfo.length > o) {
            m = this._elementRenderInfo[o].valueAxis
        }
        for (var H = 0; H < C.items.length; H++) {
            var F = C.items[H];
            var B = (j.formatFunction) ? j.formatFunction(F) : this._formatNumber(F, J);
            var A = {key: F, text: B};
            if (m && m.itemOffsets[F]) {
                A.x = m.itemOffsets[F].x;
                A.y = m.itemOffsets[F].y
            }
            D.push(A)
        }
        var M = j.gridLinesInterval || j.unitInterval;
        if (isNaN(M) || (f && M < E)) {
            M = E
        }
        var L = {visible: (j.showGridLines != false), color: (j.gridLinesColor || "#888888"), unitInterval: M, dashStyle: j.gridLinesDashStyle};
        var t = j.tickMarksInterval || j.unitInterval;
        if (isNaN(t) || (f && t < E)) {
            t = E
        }
        var s = {visible: (j.showTickMarks != false), color: (j.tickMarksColor || "#888888"), unitInterval: t, dashStyle: j.tickMarksDashStyle};
        var c = (e && j.position == "top") || (!e && j.position == "right") || (!e && this.rtl && j.position != "left");
        var I = {items: D, renderData: C};
        var G = this._getAnimProps(o);
        var b = G.enabled && D.length < 500 ? G.duration : 0;
        if (this.enableAxisTextAnimation == false) {
            b = 0
        }
        return this._renderAxis(!e, c, v, d, h, p, E, f, r, I, L, s, l, b)
    }, _buildStats: function (G) {
        var Q = {seriesGroups: new Array()};
        this._stats = Q;
        for (var m = 0; m < this.seriesGroups.length; m++) {
            var z = this.seriesGroups[m];
            Q.seriesGroups[m] = {};
            var u = Q.seriesGroups[m];
            u.isValid = true;
            var N = z.valueAxis != undefined;
            var H = (z.orientation == "horizontal") ? G.width : G.height;
            var J = false;
            var I = 10;
            if (N) {
                J = z.valueAxis.logarithmicScale == true;
                I = z.valueAxis.logarithmicScaleBase;
                if (isNaN(I)) {
                    I = 10
                }
            }
            var C = -1 != z.type.indexOf("stacked");
            var d = C && -1 != z.type.indexOf("100");
            var F = -1 != z.type.indexOf("range");
            if (d) {
                u.psums = new Array();
                u.nsums = new Array()
            }
            var n = NaN, L = NaN;
            var c = NaN, e = NaN;
            var k = z.baselineValue;
            if (isNaN(k)) {
                k = J && !d ? 1 : 0
            }
            var w = this._getDataLen(m);
            var b = 0;
            var S = NaN;
            for (var P = 0; P < w && u.isValid; P++) {
                var T = N ? z.valueAxis.minValue : Infinity;
                var B = N ? z.valueAxis.maxValue : -Infinity;
                var o = 0, r = 0;
                for (var f = 0; f < z.series.length; f++) {
                    if (!this._isSerieVisible(m, f)) {
                        continue
                    }
                    var E = undefined, O = undefined, v = undefined;
                    if (F) {
                        var U = this._getDataValueAsNumber(P, z.series[f].dataFieldFrom, m);
                        var A = this._getDataValueAsNumber(P, z.series[f].dataFieldTo, m);
                        O = Math.max(U, A);
                        v = Math.min(U, A)
                    } else {
                        E = this._getDataValueAsNumber(P, z.series[f].dataField, m);
                        if (isNaN(E) || (J && E <= 0)) {
                            continue
                        }
                        v = O = E
                    }
                    if ((isNaN(B) || O > B) && ((!N || isNaN(z.valueAxis.maxValue)) ? true : O <= z.valueAxis.maxValue)) {
                        B = O
                    }
                    if ((isNaN(T) || v < T) && ((!N || isNaN(z.valueAxis.minValue)) ? true : v >= z.valueAxis.minValue)) {
                        T = v
                    }
                    if (!isNaN(E)) {
                        if (E > k) {
                            o += E
                        } else {
                            if (E < k) {
                                r += E
                            }
                        }
                    }
                }
                if (J && d) {
                    for (var f = 0; f < z.series.length; f++) {
                        if (!this._isSerieVisible(m, f)) {
                            continue
                        }
                        var E = this._getDataValueAsNumber(P, z.series[f].dataField, m);
                        if (isNaN(E) || E <= 0) {
                            continue
                        }
                        var M = o == 0 ? 0 : E / o;
                        if (isNaN(S) || M < S) {
                            S = M
                        }
                    }
                }
                var j = o - r;
                if (b < j) {
                    b = j
                }
                if (d) {
                    u.psums[P] = o;
                    u.nsums[P] = r
                }
                if (B > L || isNaN(L)) {
                    L = B
                }
                if (T < n || isNaN(n)) {
                    n = T
                }
                if (o > c || isNaN(c)) {
                    c = o
                }
                if (r < e || isNaN(e)) {
                    e = r
                }
            }
            if (d) {
                c = c == 0 ? 0 : Math.max(c, -e);
                e = e == 0 ? 0 : Math.min(e, -c)
            }
            var h = N ? z.valueAxis.unitInterval : 0;
            if (!h) {
                if (N) {
                    h = this._calcInterval(C ? e : n, C ? c : L, Math.max(H / 80, 2))
                } else {
                    h = C ? (c - e) / 10 : (L - n) / 10
                }
            }
            var t = NaN;
            var R = 0;
            var l = 0;
            if (J) {
                if (d) {
                    t = 0;
                    var M = 1;
                    R = l = a.jqx.log(100, I);
                    while (M > S) {
                        M /= I;
                        R--;
                        t++
                    }
                    n = Math.pow(I, R)
                } else {
                    if (C) {
                        L = Math.max(L, c)
                    }
                    l = a.jqx._rnd(a.jqx.log(L, I), 1, true);
                    L = Math.pow(I, l);
                    R = a.jqx._rnd(a.jqx.log(n, I), 1, false);
                    n = Math.pow(I, R)
                }
                h = I
            }
            var K = N ? z.valueAxis.tickMarksInterval || h : 0;
            var s = N ? z.valueAxis.gridLinesInterval || h : 0;
            if (n < e) {
                e = n
            }
            if (L > c) {
                c = L
            }
            var q = J ? n : a.jqx._rnd(C ? e : n, h, false);
            var g = J ? L : a.jqx._rnd(C ? c : L, h, true);
            if (d && g > 100) {
                g = 100
            }
            if (d && !J) {
                g = (g > 0) ? 100 : 0;
                q = (q < 0) ? -100 : 0;
                h = N ? z.valueAxis.unitInterval : 10;
                if (isNaN(h) || h <= 0 || h >= 100) {
                    h = 10
                }
                if (K <= 0 || K >= 100) {
                    K = 10
                }
                if (s <= 0 || s >= 100) {
                    s = 10
                }
            }
            if (isNaN(g) || isNaN(q) || isNaN(h)) {
                continue
            }
            if (isNaN(t)) {
                t = parseInt(((g - q) / (h == 0 ? 1 : h)).toFixed())
            }
            if (J && !d) {
                t = l - R;
                b = Math.pow(I, t)
            }
            if (t < 1) {
                continue
            }
            var D = g - q;
            u.rmax = C ? c : L;
            u.rmin = C ? e : n;
            u.min = q;
            u.max = g;
            u.minPow = R;
            u.maxPow = l;
            u.mu = h;
            u.maxRange = b;
            u.intervals = t;
            u.tickMarksInterval = K;
            u.tickMarksIntervals = K == 0 ? 0 : D / K;
            u.gridLinesInterval = s;
            u.gridLinesIntervals = s == 0 ? 0 : D / s;
            if (D == 0) {
                D = 1
            }
            u.scale = C ? (c - e) / D : (L - n) / D
        }
    }, _getDataLen: function (c) {
        var b = this.source;
        if (c != undefined && c != -1 && this.seriesGroups[c].source) {
            b = this.seriesGroups[c].source
        }
        if (b instanceof a.jqx.dataAdapter) {
            b = b.records
        }
        if (b) {
            return b.length
        }
        return 0
    }, _getDataValue: function (b, e, d) {
        var c = this.source;
        if (d != undefined && d != -1) {
            c = this.seriesGroups[d].source || c
        }
        if (c instanceof a.jqx.dataAdapter) {
            c = c.records
        }
        if (!c || b < 0 || b > c.length - 1) {
            return NaN
        }
        return(e && e != "") ? c[b][e] : c[b]
    }, _getDataValueAsNumber: function (b, e, c) {
        var d = this._getDataValue(b, e, c);
        if (this._isDate(d)) {
            return d.valueOf()
        }
        if (typeof(d) != "number") {
            d = parseFloat(d)
        }
        if (typeof(d) != "number") {
            d = undefined
        }
        return d
    }, _renderPieSeries: function (o, l) {
        var c = this._getDataLen(o);
        var n = this.seriesGroups[o];
        var p = this._calcGroupOffsets(o, l).offsets;
        for (var m = 0; m < n.series.length; m++) {
            var t = n.series[m];
            var d = this._getSerieSettings(o, m);
            var k = t.colorScheme || n.colorScheme || this.colorScheme;
            var f = this._getAnimProps(o, m);
            var e = f.enabled && c < 5000 && !this._isToggleRefresh && this._isVML != true ? f.duration : 0;
            if (a.jqx.mobile.isMobileBrowser() && (this.renderer instanceof a.jqx.HTML5Renderer)) {
                e = 0
            }
            var r = {rect: l, groupIndex: o, serieIndex: m, settings: d, items: []};
            for (var h = 0; h < c; h++) {
                if (!this._isSerieVisible(o, m, h)) {
                    continue
                }
                var j = p[m][h];
                var g = this.renderer.pieslice(j.x, j.y, j.innerRadius, j.outerRadius, j.fromAngle, e == 0 ? j.toAngle : j.fromAngle, j.centerOffset);
                var b = {element: g, displayValue: j.displayValue, itemIndex: h, x: j.x, y: j.y, innerRadius: j.innerRadius, outerRadius: j.outerRadius, fromAngle: j.fromAngle, toAngle: j.toAngle, centerOffset: j.centerOffset};
                r.items.push(b)
            }
            this._animatePieSlices(r, 0);
            var q = this;
            this._enqueueAnimation("series", g, undefined, e, function (s, i, u) {
                q._animatePieSlices(i, u)
            }, r)
        }
    }, _sliceSortFunction: function (d, c) {
        return d.fromAngle - c.fromAngle
    }, _animatePieSlices: function (w, c) {
        var o;
        if (this._elementRenderInfo && this._elementRenderInfo.length > w.groupIndex && this._elementRenderInfo[w.groupIndex].series && this._elementRenderInfo[w.groupIndex].series.length > w.serieIndex) {
            o = this._elementRenderInfo[w.groupIndex].series[w.serieIndex]
        }
        var h = 360 * c;
        var b = [];
        for (var B = 0; B < w.items.length; B++) {
            var G = w.items[B];
            var z = G.fromAngle;
            var f = G.fromAngle + c * (G.toAngle - G.fromAngle);
            if (o && o[G.displayValue]) {
                var t = o[G.displayValue].fromAngle;
                var d = o[G.displayValue].toAngle;
                z = t + (z - t) * c;
                f = d + (f - d) * c
            }
            b.push({index: B, from: z, to: f})
        }
        if (o) {
            b.sort(this._sliceSortFunction)
        }
        var H = NaN;
        for (var B = 0; B < b.length; B++) {
            var G = w.items[b[B].index];
            var z = b[B].from;
            var f = b[B].to;
            if (o) {
                if (!isNaN(H) && z > H) {
                    z = H
                }
                H = f;
                if (B == b.length - 1 && f != b[0].from) {
                    f = 360 + b[0].from
                }
            }
            var A = this.renderer.pieSlicePath(G.x, G.y, G.innerRadius, G.outerRadius, z, f, G.centerOffset);
            this.renderer.attr(G.element, {d: A});
            var l = this._getColors(w.groupIndex, w.serieIndex, G.itemIndex, "radialGradient", G.outerRadius);
            var F = w.settings;
            this.renderer.attr(G.element, {fill: l.fillColor, stroke: l.lineColor, "stroke-width": F.stroke, "fill-opacity": F.opacity, "stroke-opacity": F.opacity, "stroke-dasharray": "none" || F.dashStyle});
            if (G.labelElement) {
                this.renderer.removeElement(G.labelElement)
            }
            var J = z, K = f;
            var p = Math.abs(J - K);
            var v = p > 180 ? 1 : 0;
            if (p > 360) {
                J = 0;
                K = 360
            }
            var u = J * Math.PI * 2 / 360;
            var j = K * Math.PI * 2 / 360;
            var n = p / 2 + J;
            var I = n * Math.PI * 2 / 360;
            var r = this._showLabel(w.groupIndex, w.serieIndex, G.itemIndex, {x: 0, y: 0, width: 0, height: 0}, "left", "top", true);
            var C = this.seriesGroups[w.groupIndex];
            var q = C.series[w.serieIndex];
            var e = q.labelRadius || G.outerRadius + Math.max(r.width, r.height);
            e += G.centerOffset;
            var E = a.jqx.getNum([q.offsetX, C.offsetX, w.rect.width / 2]);
            var D = a.jqx.getNum([q.offsetY, C.offsetY, w.rect.height / 2]);
            var m = a.jqx._ptrnd(w.rect.x + E + e * Math.cos(I) - r.width / 2);
            var k = a.jqx._ptrnd(w.rect.y + D - e * Math.sin(I) - r.height / 2);
            G.labelElement = this._showLabel(w.groupIndex, w.serieIndex, G.itemIndex, {x: m, y: k, width: r.width, height: r.height}, "left", "top");
            if (c == 1) {
                this._installHandlers(G.element, w.groupIndex, w.serieIndex, G.itemIndex)
            }
        }
    }, _getColumnGroupsCount: function (c) {
        var e = 0;
        c = c || "vertical";
        var f = this.seriesGroups;
        for (var d = 0; d < f.length; d++) {
            var b = f[d].orientation || "vertical";
            if (f[d].type.indexOf("column") != -1 && b == c) {
                e++
            }
        }
        return e
    }, _getColumnGroupIndex: function (g) {
        var b = 0;
        var c = this.seriesGroups[g].orientation || "vertical";
        for (var e = 0; e < g; e++) {
            var f = this.seriesGroups[e];
            var d = f.orientation || "vertical";
            if (f.type.indexOf("column") != -1 && d == c) {
                b++
            }
        }
        return b
    }, _renderBand: function (p, l, j) {
        var o = this.seriesGroups[p];
        if (!o.bands || o.bands.length <= l) {
            return
        }
        var c = j;
        if (o.orientation == "horizontal") {
            c = {x: j.y, y: j.x, width: j.height, height: j.width}
        }
        var q = this._calcGroupOffsets(p, c);
        if (!q || q.length <= p) {
            return
        }
        var r = o.bands[l];
        var g = q.bands[l];
        var n = g.from;
        var m = g.to;
        var e = Math.abs(n - m);
        var i = {x: c.x, y: Math.min(n, m), width: c.width, height: e};
        if (o.orientation == "horizontal") {
            var d = i.x;
            i.x = i.y;
            i.y = d;
            d = i.width;
            i.width = i.height;
            i.height = d
        }
        var k = this.renderer.rect(i.x, i.y, i.width, i.height);
        var b = r.color || "#AAAAAA";
        var f = r.opacity;
        if (isNaN(f) || f < 0 || f > 1) {
            f = 0.5
        }
        this.renderer.attr(k, {fill: b, "fill-opacity": f, stroke: b, "stroke-opacity": f, "stroke-width": 0})
    }, _renderColumnSeries: function (h, C) {
        var q = this.seriesGroups[h];
        if (!q.series || q.series.length == 0) {
            return
        }
        var v = q.type.indexOf("stacked") != -1;
        var d = v && q.type.indexOf("100") != -1;
        var A = q.type.indexOf("range") != -1;
        var n = this._getDataLen(h);
        var J = q.columnsGapPercent;
        if (isNaN(J) || J < 0 || J > 100) {
            J = 25
        }
        var K = q.seriesGapPercent;
        if (isNaN(K) || K < 0 || K > 100) {
            K = 10
        }
        var r = q.orientation == "horizontal";
        var l = C;
        if (r) {
            l = {x: C.y, y: C.x, width: C.height, height: C.width}
        }
        var o = this._calcGroupOffsets(h, l);
        if (!o || o.xoffsets.length == 0) {
            return
        }
        var f = this._getColumnGroupsCount(q.orientation);
        var b = this._getColumnGroupIndex(h);
        if (this.columnSeriesOverlap == true) {
            f = 1;
            b = 0
        }
        var M = this._alignValuesWithTicks(h);
        var c;
        if (q.polar == true || q.spider == true) {
            c = this._getPolarAxisCoords(h, l);
            J = 0;
            K = 0
        }
        var t = {groupIndex: h, rect: C, vertical: !r, seriesCtx: [], renderData: o, polarAxisCoords: c};
        for (var i = 0; i < q.series.length; i++) {
            var I = q.series[i];
            var E = I.columnsMaxWidth || q.columnsMaxWidth;
            var u = I.dataField;
            var G = this._getAnimProps(h, i);
            var w = G.enabled && !this._isToggleRefresh && o.xoffsets.length < 100 ? G.duration : 0;
            var H = 0;
            var e = o.xoffsets.itemWidth;
            if (M) {
                H -= e / 2
            }
            H += e * (b / f);
            e /= f;
            var p = H + e;
            var B = (p - H + 1);
            var D = (p - H + 1) / (1 + J / 100);
            var m = (!v && q.series.length > 1) ? (D * K / 100) / (q.series.length - 1) : 0;
            var z = (D - m * (q.series.length - 1));
            if (D < 1) {
                D = 1
            }
            var j = 0;
            if (!v && q.series.length > 1) {
                z /= q.series.length;
                j = i
            }
            var N = H + (B - D) / 2 + j * (m + z);
            if (j == q.series.length) {
                z = B - H + D - x
            }
            if (!isNaN(E)) {
                var F = Math.min(z, E);
                N = N + (z - F) / 2;
                z = F
            }
            var g = this._isSerieVisible(h, i);
            var L = {seriesIndex: i, columnWidth: z, xAdjust: N, isVisible: g};
            t.seriesCtx.push(L)
        }
        this._animateColumns(t, w == 0 ? 1 : 0);
        var k = this;
        this._enqueueAnimation("series", undefined, undefined, w, function (O, s, P) {
            k._animateColumns(s, P)
        }, t)
    }, _getColumnOffsets: function (n, e, p, z, j, b) {
        var g = [];
        var f = NaN;
        for (var w = 0; w < p.length; w++) {
            var u = p[w];
            var q = u.seriesIndex;
            var h = this.seriesGroups[e];
            var m = h.series[q];
            var r = n.offsets[q][z].from;
            var c = n.offsets[q][z].to;
            var A = n.xoffsets.data[z];
            var d = undefined;
            var o = u.isVisible;
            if (!o) {
                c = r
            }
            if (o && this._elementRenderInfo && this._elementRenderInfo.length > e) {
                var k = n.xoffsets.xvalues[z];
                d = this._elementRenderInfo[e].series[q][k];
                if (d && !isNaN(d.from) && !isNaN(d.to)) {
                    r = d.from + (r - d.from) * b;
                    if (!isNaN(f) && j && r != f) {
                        r = f
                    }
                    c = d.to + (c - d.to) * b;
                    A = d.xoffset + (A - d.xoffset) * b
                }
            }
            if (!d) {
                c = r + (c - r) * (j ? 1 : b)
            }
            f = c;
            g.push({from: r, to: c, xOffset: A})
        }
        if (j && g.length > 1 && !(this._elementRenderInfo && this._elementRenderInfo.length > e)) {
            var l = g[0].from + (f - g[0].from) * b;
            for (var v = 0; v < g.length; v++) {
                if (g[v].to < g[v].from) {
                    if (g[v].to < l) {
                        g[v].to = l
                    }
                    if (g[v].from < l) {
                        g[v].from = l
                    }
                } else {
                    if (g[v].to > l) {
                        g[v].to = l
                    }
                    if (g[v].from > l) {
                        g[v].from = l
                    }
                }
            }
        }
        return g
    }, _columnAsPieSlice: function (b, f, l, n, o) {
        var e = this._toPolarCoord(n, l, o.x, o.y);
        var g = this._toPolarCoord(n, l, o.x, o.y + o.height);
        var p = this._toPolarCoord(n, l, o.x + o.width, o.y);
        var m = a.jqx._ptdist(n.x, n.y, g.x, g.y);
        var j = a.jqx._ptdist(n.x, n.y, e.x, e.y);
        var d = l.width;
        var c = -((o.x - l.x) * 360) / d;
        var i = -((o.x + o.width - l.x) * 360) / d;
        var k = n.startAngle;
        k = 360 * k / (Math.PI * 2);
        c -= k;
        i -= k;
        if (b[f] != undefined) {
            var h = this.renderer.pieSlicePath(n.x, n.y, m, j, i, c, 0);
            this.renderer.attr(b[f], {d: h})
        } else {
            b[f] = this.renderer.pieslice(n.x, n.y, m, j, i, c, 0)
        }
        return{fromAngle: i, toAngle: c, innerRadius: m, outerRadius: j}
    }, _animateColumns: function (e, b) {
        var D = e.groupIndex;
        var h = this.seriesGroups[D];
        var r = e.renderData;
        var j = h.type.indexOf("stacked") != -1;
        var m = e.polarAxisCoords;
        for (var B = r.xoffsets.first; B <= r.xoffsets.last; B++) {
            var g = this._getColumnOffsets(r, D, e.seriesCtx, B, j, b);
            for (var A = 0; A < e.seriesCtx.length; A++) {
                var w = e.seriesCtx[A];
                var u = w.seriesIndex;
                var o = h.series[u];
                var v = g[A].from;
                var d = g[A].to;
                var E = g[A].xOffset;
                if (!w.elements) {
                    w.elements = {}
                }
                if (!w.labelElements) {
                    w.labelElements = {}
                }
                var n = w.elements;
                var q = w.labelElements;
                var z = (e.vertical ? e.rect.x : e.rect.y) + w.xAdjust;
                var C = this._getSerieSettings(D, u);
                var k = C.colors;
                var t = this._isSerieVisible(D, u);
                if (!t) {
                    if (!j) {
                        continue
                    }
                }
                var l = a.jqx._ptrnd(z + E);
                var c = {x: l, width: w.columnWidth};
                if (e.vertical) {
                    c.y = v;
                    c.height = d - v;
                    if (c.height < 0) {
                        c.y += c.height;
                        c.height = -c.height
                    }
                } else {
                    c.x = v < d ? v : d;
                    c.width = Math.abs(v - d);
                    c.y = l;
                    c.height = w.columnWidth
                }
                var p = v - d;
                if (isNaN(p)) {
                    continue
                }
                p = Math.abs(p);
                if (n[B] == undefined) {
                    if (!m) {
                        n[B] = this.renderer.rect(c.x, c.y, e.vertical ? c.width : 0, e.vertical ? 0 : c.height)
                    } else {
                        this._columnAsPieSlice(n, B, e.rect, m, c)
                    }
                    this.renderer.attr(n[B], {fill: k.fillColor, "fill-opacity": C.opacity, "stroke-opacity": C.opacity, stroke: k.lineColor, "stroke-width": C.stroke, "stroke-dasharray": C.dashStyle})
                }
                if (p < 1 && b != 1) {
                    this.renderer.attr(n[B], {display: "none"})
                } else {
                    this.renderer.attr(n[B], {display: "block"})
                }
                if (m) {
                    var f = this._columnAsPieSlice(n, B, e.rect, m, c);
                    var k = this._getColors(D, u, undefined, "radialGradient", f.outerRadius);
                    this.renderer.attr(n[B], {fill: k.fillColor, "fill-opacity": C.opacity, "stroke-opacity": C.opacity, stroke: k.lineColor, "stroke-width": C.stroke, "stroke-dasharray": C.dashStyle})
                } else {
                    if (e.vertical == true) {
                        this.renderer.attr(n[B], {x: c.x, y: c.y, height: p})
                    } else {
                        this.renderer.attr(n[B], {x: c.x, y: c.y, width: p})
                    }
                }
                this.renderer.removeElement(q[B]);
                q[B] = this._showLabel(D, u, B, c);
                if (b == 1) {
                    this._installHandlers(n[B], D, u, B)
                }
            }
        }
    }, _renderScatterSeries: function (d, z) {
        var o = this.seriesGroups[d];
        if (!o.series || o.series.length == 0) {
            return
        }
        var e = o.type == "bubble";
        var p = o.orientation == "horizontal";
        var k = z;
        if (p) {
            k = {x: z.y, y: z.x, width: z.height, height: z.width}
        }
        var l = this._calcGroupOffsets(d, k);
        if (!l || l.xoffsets.length == 0) {
            return
        }
        var I = k.width;
        var b;
        if (o.polar || o.spider) {
            b = this._getPolarAxisCoords(d, k);
            I = 2 * b.r
        }
        var Q = this._alignValuesWithTicks(d);
        for (var f = 0; f < o.series.length; f++) {
            var O = this._getSerieSettings(d, f);
            var G = O.colors;
            var F = o.series[f];
            var u = F.dataField;
            var P = NaN, t = NaN;
            if (e) {
                for (var N = l.xoffsets.first; N <= l.xoffsets.last; N++) {
                    var w = this._getDataValueAsNumber(N, F.radiusDataField, d);
                    if (typeof(w) != "number") {
                        throw"Invalid radiusDataField value at [" + N + "]"
                    }
                    if (!isNaN(w)) {
                        if (isNaN(P) || w < P) {
                            P = w
                        }
                        if (isNaN(t) || w > t) {
                            t = w
                        }
                    }
                }
            }
            var h = F.minRadius;
            if (isNaN(h)) {
                h = I / 50
            }
            var A = F.maxRadius;
            if (isNaN(A)) {
                A = I / 25
            }
            if (h > A) {
                A = h
            }
            var H = F.radius || 5;
            var B = this._getAnimProps(d, f);
            var v = B.enabled && !this._isToggleRefresh && l.xoffsets.length < 5000 ? B.duration : 0;
            var q = {groupIndex: d, seriesIndex: f, fill: G.fillColor, "fill-opacity": O.opacity, "stroke-opacity": O.opacity, stroke: G.lineColor, "stroke-width": O.stroke, "stroke-dasharray": O.dashStyle, items: [], polarAxisCoords: b};
            for (var N = l.xoffsets.first; N <= l.xoffsets.last; N++) {
                var w = this._getDataValueAsNumber(N, u, d);
                if (typeof(w) != "number") {
                    continue
                }
                var E = l.xoffsets.data[N];
                var D = l.offsets[f][N].to;
                var C = l.xoffsets.xvalues[N];
                if (isNaN(E) || isNaN(D)) {
                    continue
                }
                if (p) {
                    var K = E;
                    E = D;
                    D = K + z.y
                } else {
                    E += z.x
                }
                var J = H;
                if (e) {
                    var m = this._getDataValueAsNumber(N, F.radiusDataField, d);
                    if (typeof(m) != "number") {
                        continue
                    }
                    J = h + (A - h) * (m - P) / Math.max(1, t - P);
                    if (isNaN(J)) {
                        J = h
                    }
                }
                var j = NaN, L = NaN;
                var n = 0;
                if (C != undefined && this._elementRenderInfo && this._elementRenderInfo.length > d) {
                    var c = this._elementRenderInfo[d].series[f][C];
                    if (c && !isNaN(c.to)) {
                        j = c.to;
                        L = c.xoffset;
                        n = H;
                        if (p) {
                            var K = L;
                            L = j;
                            j = K + z.y
                        } else {
                            L += z.x
                        }
                        if (e) {
                            n = h + (A - h) * (c.valueRadius - P) / Math.max(1, t - P);
                            if (isNaN(n)) {
                                n = h
                            }
                        }
                    }
                }
                q.items.push({from: n, to: J, itemIndex: N, x: E, y: D, xFrom: L, yFrom: j})
            }
            this._animR(q, 0);
            var g = this;
            var M = undefined;
            this._enqueueAnimation("series", undefined, undefined, v, function (s, i, r) {
                g._animR(i, r)
            }, q)
        }
    }, _animR: function (l, e) {
        var f = l.items;
        for (var d = 0; d < f.length; d++) {
            var k = f[d];
            var h = k.x;
            var g = k.y;
            var b = Math.round((k.to - k.from) * e + k.from);
            if (!isNaN(k.yFrom)) {
                g = k.yFrom + (g - k.yFrom) * e
            }
            if (!isNaN(k.xFrom)) {
                h = k.xFrom + (h - k.xFrom) * e
            }
            if (l.polarAxisCoords) {
                var j = this._toPolarCoord(l.polarAxisCoords, this._plotRect, h, g);
                h = j.x;
                g = j.y
            }
            h = a.jqx._ptrnd(h);
            g = a.jqx._ptrnd(g);
            b = a.jqx._ptrnd(b);
            var c = k.element;
            if (!c) {
                c = this.renderer.circle(h, g, b);
                this.renderer.attr(c, {fill: l.fill, "fill-opacity": l["fill-opacity"], "stroke-opacity": l["fill-opacity"], stroke: l.stroke, "stroke-width": l["stroke-width"], "stroke-dasharray": l["stroke-dasharray"]});
                k.element = c
            }
            if (this._isVML) {
                this.renderer.updateCircle(c, undefined, undefined, b)
            } else {
                this.renderer.attr(c, {r: b, cy: g, cx: h})
            }
            if (k.labelElement) {
                this.renderer.removeElement(k.labelElement)
            }
            k.labelElement = this._showLabel(l.groupIndex, l.seriesIndex, k.itemIndex, {x: h - b, y: g - b, width: 2 * b, height: 2 * b});
            if (e >= 1) {
                this._installHandlers(c, l.groupIndex, l.seriesIndex, k.itemIndex)
            }
        }
    }, _showToolTip: function (m, k, E, z, c) {
        var u = this._getCategoryAxis(E);
        if (this._toolTipElement && E == this._toolTipElement.gidx && z == this._toolTipElement.sidx && c == this._toolTipElement.iidx) {
            return
        }
        var j = this.seriesGroups[E];
        var n = j.series[z];
        var g = this.enableCrosshairs && !(j.polar || j.spider);
        if (this._pointMarker) {
            m = parseInt(this._pointMarker.x + 5);
            k = parseInt(this._pointMarker.y - 5)
        } else {
            g = false
        }
        var i = g && this.showToolTips == false;
        m = a.jqx._ptrnd(m);
        k = a.jqx._ptrnd(k);
        var F = this._toolTipElement == undefined;
        if (j.showToolTips == false || n.showToolTips == false) {
            return
        }
        var f = n.toolTipFormatSettings || j.toolTipFormatSettings;
        var t = n.toolTipFormatFunction || j.toolTipFormatFunction || this.toolTipFormatFunction;
        var l = this._getColors(E, z, c);
        var b = this._getDataValue(c, u.dataField, E);
        if (u.dataField == undefined || u.dataField == "") {
            b = c
        }
        if (u.type == "date") {
            b = this._castAsDate(b)
        }
        var q = "";
        if (a.isFunction(t)) {
            var w = {};
            if (j.type.indexOf("range") == -1) {
                w = this._getDataValue(c, n.dataField, E)
            } else {
                w.from = this._getDataValue(c, n.dataFieldFrom, E);
                w.to = this._getDataValue(c, n.dataFieldTo, E)
            }
            q = t(w, c, n, j, b, u)
        } else {
            q = this._getFormattedValue(E, z, c, f, t);
            var J = u.toolTipFormatSettings || u.formatSettings;
            var d = u.toolTipFormatFunction || u.formatFunction;
            var I = this._formatValue(b, J, d);
            if (j.type != "pie" && j.type != "donut") {
                q = (n.displayText || n.dataField || "") + ", " + I + ": " + q
            } else {
                b = this._getDataValue(c, n.displayText || n.dataField, E);
                I = this._formatValue(b, J, d);
                q = I + ": " + q
            }
        }
        var D = n.toolTipClass || j.toolTipClass || this.toThemeProperty("jqx-chart-tooltip-text", null);
        var G = n.toolTipBackground || j.toolTipBackground || "#FFFFFF";
        var H = n.toolTipLineColor || j.toolTipLineColor || l.lineColor;
        if (!this._toolTipElement) {
            this._toolTipElement = {}
        }
        this._toolTipElement.sidx = z;
        this._toolTipElement.gidx = E;
        this._toolTipElement.iidx = c;
        rect = this.renderer.getRect();
        if (g) {
            var C = a.jqx._ptrnd(this._pointMarker.x);
            var B = a.jqx._ptrnd(this._pointMarker.y);
            if (this._toolTipElement.vLine && this._toolTipElement.hLine) {
                this.renderer.attr(this._toolTipElement.vLine, {x1: C, x2: C});
                this.renderer.attr(this._toolTipElement.hLine, {y1: B, y2: B})
            } else {
                var A = this.crosshairsColor || "#888888";
                this._toolTipElement.vLine = this.renderer.line(C, this._plotRect.y, C, this._plotRect.y + this._plotRect.height, {stroke: A, "stroke-width": this.crosshairsLineWidth || 1, "stroke-dasharray": this.crosshairsDashStyle || ""});
                this._toolTipElement.hLine = this.renderer.line(this._plotRect.x, B, this._plotRect.x + this._plotRect.width, B, {stroke: A, "stroke-width": this.crosshairsLineWidth || 1, "stroke-dasharray": this.crosshairsDashStyle || ""})
            }
        }
        if (!i && this.showToolTips != false) {
            var s = !F ? this._toolTipElement.box : document.createElement("div");
            var e = {left: 0, top: 0};
            if (F) {
                s.style.position = "absolute";
                s.style.cursor = "default";
                s.style.overflow = "hidden";
                a(s).addClass("jqx-rc-all jqx-button");
                a(document.body).append(s);
                var v = this
            }
            s.style.backgroundColor = G;
            s.style.borderColor = H;
            this._toolTipElement.box = s;
            this._toolTipElement.txt = q;
            var o = "<span class='" + D + "'>" + q + "</span>";
            var h = this._toolTipElement.tmp;
            if (F) {
                this._toolTipElement.tmp = h = document.createElement("div");
                h.style.position = "absolute";
                h.style.cursor = "default";
                h.style.overflow = "hidden";
                h.style.display = "none";
                h.style.zIndex = 999999;
                h.style.backgroundColor = G;
                h.style.borderColor = H;
                a(h).addClass("jqx-rc-all jqx-button");
                this.host.append(h)
            }
            a(h).html(o);
            var r = {width: a(h).width(), height: a(h).height()};
            r.width = r.width + 5;
            r.height = r.height + 6;
            m = Math.max(m, rect.x);
            k = Math.max(k - r.height, rect.y);
            if (r.width > rect.width || r.height > rect.height) {
                return
            }
            if (m + e.left + r.width > rect.x + rect.width - 5) {
                m = rect.x + rect.width - r.width - e.left - 5;
                s.style.left = e.left + m + "px"
            }
            if (k + e.top + r.height > rect.y + rect.height - 5) {
                k = rect.y + rect.height - r.height - 5;
                s.style.top = e.top + k + "px"
            }
            var p = this.host.coord();
            if (F) {
                a(s).fadeOut(0, 0);
                s.style.left = e.left + m + p.left + "px";
                s.style.top = e.top + k + p.top + "px"
            }
            a(s).html(o);
            a(s).clearQueue();
            a(s).animate({left: e.left + m + p.left, top: e.top + k + p.top, opacity: 1}, 300, "easeInOutCirc");
            a(s).fadeTo(400, 1)
        }
    }, _hideToolTip: function (b) {
        if (!this._toolTipElement) {
            return
        }
        if (this._toolTipElement.box) {
            if (b == 0) {
                a(this._toolTipElement.box).hide()
            } else {
                a(this._toolTipElement.box).fadeOut()
            }
        }
        this._hideCrosshairs();
        this._toolTipElement.gidx = undefined
    }, _hideCrosshairs: function () {
        if (!this._toolTipElement) {
            return
        }
        if (this._toolTipElement.vLine) {
            this.renderer.removeElement(this._toolTipElement.vLine);
            this._toolTipElement.vLine = undefined
        }
        if (this._toolTipElement.hLine) {
            this.renderer.removeElement(this._toolTipElement.hLine);
            this._toolTipElement.hLine = undefined
        }
    }, _showLabel: function (u, r, d, b, m, f, c) {
        var g = this.seriesGroups[u];
        var k = g.series[r];
        var p = {width: 0, height: 0};
        if (k.showLabels == false || (!k.showLabels && !g.showLabels)) {
            return c ? p : undefined
        }
        if (b.width < 0 || b.height < 0) {
            return c ? p : undefined
        }
        var e = k.labelAngle || k.labelsAngle || g.labelAngle || g.labelsAngle || 0;
        var s = k.labelOffset || g.labelOffset || {x: 0, y: 0};
        var q = k.labelClass || g.labelClass || this.toThemeProperty("jqx-chart-label-text", null);
        m = m || "center";
        f = f || "center";
        var o = this._getFormattedValue(u, r, d);
        var l = b.width;
        var t = b.height;
        p = this.renderer.measureText(o, e, {"class": q});
        if (c) {
            return p
        }
        var j = 0;
        if (m == "" || m == "center") {
            j += (l - p.width) / 2
        } else {
            if (m == "right") {
                j += (l - p.width)
            }
        }
        var i = 0;
        if (f == "" || f == "center") {
            i += (t - p.height) / 2
        } else {
            if (f == "bottom") {
                i += (t - p.height)
            }
        }
        var n = this.renderer.text(o, j + b.x + s.x, i + b.y + s.y, p.width, p.height, e, {}, false, "center", "center");
        this.renderer.attr(n, {"class": q});
        if (this._isVML) {
            this.renderer.removeElement(n);
            this.renderer.getContainer()[0].appendChild(n)
        }
        return n
    }, _getAnimProps: function (j, f) {
        var e = this.seriesGroups[j];
        var c = !isNaN(f) ? e.series[f] : undefined;
        var b = this.enableAnimations == true;
        if (e.enableAnimations) {
            b = e.enableAnimations == true
        }
        if (c && c.enableAnimations) {
            b = c.enableAnimations == true
        }
        var i = this.animationDuration;
        if (isNaN(i)) {
            i = 1000
        }
        var d = e.animationDuration;
        if (!isNaN(d)) {
            i = d
        }
        if (c) {
            var h = c.animationDuration;
            if (!isNaN(h)) {
                i = h
            }
        }
        if (i > 5000) {
            i = 1000
        }
        return{enabled: b, duration: i}
    }, _renderLineSeries: function (f, I) {
        var B = this.seriesGroups[f];
        if (!B.series || B.series.length == 0) {
            return
        }
        var n = B.type.indexOf("area") != -1;
        var E = B.type.indexOf("stacked") != -1;
        var b = E && B.type.indexOf("100") != -1;
        var W = B.type.indexOf("spline") != -1;
        var o = B.type.indexOf("step") != -1;
        var G = B.type.indexOf("range") != -1;
        var Y = B.polar == true || B.spider == true;
        if (Y) {
            o = false
        }
        if (o && W) {
            return
        }
        var t = this._getDataLen(f);
        var U = I.width / t;
        var aa = B.orientation == "horizontal";
        var v = this._getCategoryAxis(f).flip == true;
        var r = I;
        if (aa) {
            r = {x: I.y, y: I.x, width: I.height, height: I.width}
        }
        var w = this._calcGroupOffsets(f, r);
        if (!w || w.xoffsets.length == 0) {
            return
        }
        for (var Q = B.series.length - 1; Q >= 0; Q--) {
            var e = this._isSerieVisible(f, Q);
            if (!e) {
                continue
            }
            var X = this._getSerieSettings(f, Q);
            var M = w.xoffsets.first;
            var A = M;
            do {
                var O = [];
                var L = [];
                var m = [];
                var H = -1;
                var k = 0;
                var J = NaN;
                var z = NaN;
                var Z = NaN;
                if (w.xoffsets.length < 1) {
                    continue
                }
                var K = this._getAnimProps(f, Q);
                var F = K.enabled && !this._isToggleRefresh && w.xoffsets.length < 10000 && this._isVML != true ? K.duration : 0;
                var q = M;
                var p = false;
                for (var V = M; V <= w.xoffsets.last; V++) {
                    M = V;
                    var P = w.xoffsets.data[V];
                    var N = w.xoffsets.xvalues[V];
                    if (P == undefined) {
                        continue
                    }
                    P = Math.max(P, 1);
                    k = P;
                    var j = w.offsets[Q][V].to;
                    var T = w.offsets[Q][V].from;
                    if (isNaN(j) || isNaN(T)) {
                        M++;
                        p = true;
                        break
                    }
                    var c = undefined;
                    if (this._elementRenderInfo && this._elementRenderInfo.length > f && this._elementRenderInfo[f].series.length > Q) {
                        c = this._elementRenderInfo[f].series[Q][N];
                        var Z = a.jqx._ptrnd(c ? c.to : undefined);
                        var D = a.jqx._ptrnd(r.x + (c ? c.xoffset : undefined));
                        m.push(aa ? {y: D, x: Z, index: V} : {x: D, y: Z, index: V})
                    }
                    A = V;
                    if (!n && b) {
                        if (j <= r.y) {
                            j = r.y + 1
                        }
                        if (j >= r.y + r.height) {
                            j = r.y + r.height - 1
                        }
                        if (T <= r.y) {
                            T = r.y + 1
                        }
                        if (T >= r.y + r.height) {
                            T = r.y + r.height - 1
                        }
                    }
                    P = Math.max(P, 1);
                    k = P + r.x;
                    if (o && !isNaN(J) && !isNaN(z)) {
                        if (z != j) {
                            O.push(aa ? {y: k, x: a.jqx._ptrnd(z)} : {x: k, y: a.jqx._ptrnd(z)})
                        }
                    }
                    O.push(aa ? {y: k, x: a.jqx._ptrnd(j), index: V} : {x: k, y: a.jqx._ptrnd(j), index: V});
                    L.push(aa ? {y: k, x: a.jqx._ptrnd(T), index: V} : {x: k, y: a.jqx._ptrnd(T), index: V});
                    J = k;
                    z = j;
                    if (isNaN(Z)) {
                        Z = j
                    }
                }
                var g = r.x + w.xoffsets.data[q];
                var S = r.x + w.xoffsets.data[A];
                if (n && B.alignEndPointsWithIntervals == true) {
                    var u = v ? -1 : 1;
                    if (g > r.x) {
                        g = r.x
                    }
                    if (S < r.x + r.width) {
                        S = r.x + r.width
                    }
                    if (v) {
                        var R = g;
                        g = S;
                        S = R
                    }
                }
                S = a.jqx._ptrnd(S);
                g = a.jqx._ptrnd(g);
                var h = w.baseOffset;
                Z = a.jqx._ptrnd(Z);
                var d = a.jqx._ptrnd(j) || h;
                if (G) {
                    O = O.concat(L.reverse())
                }
                var C = {groupIndex: f, serieIndex: Q, settings: X, pointsArray: O, pointsStart: m, left: g, right: S, pyStart: Z, pyEnd: d, yBase: h, swapXY: aa, isArea: n, isSpline: W, isRange: G, isPolar: Y, labelElements: [], symbolElements: []};
                this._animateLine(C, F == 0 ? 1 : 0);
                var l = this;
                this._enqueueAnimation("series", undefined, undefined, F, function (s, i, ab) {
                    l._animateLine(i, ab)
                }, C)
            } while (M < w.xoffsets.length - 1 || p)
        }
    }, _animateLine: function (s, m) {
        var c = this._calculateLine(s, s.pointsArray, s.pointsStart, s.yBase, m, s.isArea, s.swapXY);
        if (c == "") {
            return
        }
        var o = c.split(" ");
        var b = o.length;
        var g = c;
        if (g != "") {
            g = this._buildLineCmd(c, s.isRange, s.left, s.right, s.pyStart, s.pyEnd, s.yBase, s.isArea, s.isPolar, s.isSpline, s.swapXY)
        } else {
            g = "M 0 0"
        }
        var f = s.settings;
        if (!s.pathElement) {
            s.pathElement = this.renderer.path(g, {"stroke-width": f.stroke, stroke: f.colors.lineColor, "stroke-opacity": f.opacity, "fill-opacity": f.opacity, "stroke-dasharray": f.dashStyle, fill: s.isArea ? f.colors.fillColor : "none"});
            this._installHandlers(s.pathElement, s.groupIndex, s.serieIndex)
        } else {
            this.renderer.attr(s.pathElement, {d: g})
        }
        if (s.labelElements) {
            for (var j = 0; j < s.labelElements.length; j++) {
                this.renderer.removeElement(s.labelElements[j])
            }
        }
        if (s.symbolElements) {
            for (var j = 0; j < s.symbolElements.length; j++) {
                this.renderer.removeElement(s.symbolElements[j])
            }
        }
        var e = this._getSymbol(s.groupIndex, s.serieIndex);
        if (s.pointsArray.length == o.length) {
            var l = this.seriesGroups[s.groupIndex].series[s.serieIndex];
            if (e != "none") {
                var k = s.symbolElements;
                var d = l.symbolSize;
                for (var j = 0; j < o.length; j++) {
                    var q = o[j].split(",");
                    q = {x: parseFloat(q[0]), y: parseFloat(q[1])};
                    var h = this._drawSymbol(e, q.x, q.y, f.colors.fillColorSymbol, f.colors.lineColorSymbol, 1, f.opacity, d);
                    var p = (j > 0 ? o[j - 1] : o[j]).split(",");
                    p = {x: parseFloat(p[0]), y: parseFloat(p[1])};
                    var r = (j < o.length - 1 ? o[j + 1] : o[j]).split(",");
                    r = {x: parseFloat(r[0]), y: parseFloat(r[1])};
                    q = this._adjustLineLabelPosition(s.groupIndex, s.serieIndex, s.pointsArray[j].index, q, p, r);
                    var n = this._showLabel(s.groupIndex, s.serieIndex, s.pointsArray[j].index, {x: q.x, y: q.y, width: 0, height: 0});
                    s.symbolElements.push(h);
                    s.labelElements.push(n)
                }
            }
        }
        if (m == 1 && e != "none") {
            for (var j = 0; j < s.symbolElements.length; j++) {
                this._installHandlers(s.symbolElements[j], s.groupIndex, s.serieIndex)
            }
        }
    }, _adjustLineLabelPosition: function (i, g, d, h, f, e) {
        var b = this._showLabel(i, g, d, {width: 0, height: 0}, "", "", true);
        var c = {x: 0, y: 0};
        if (h.y == f.y && h.x == f.x) {
            if (e.y < h.y) {
                c = {x: h.x, y: h.y + b.height}
            } else {
                c = {x: h.x, y: h.y - b.height}
            }
        } else {
            if (h.y == e.y && h.x == e.x) {
                if (f.y < h.y) {
                    c = {x: h.x, y: h.y + b.height}
                } else {
                    c = {x: h.x, y: h.y - b.height}
                }
            }
        }
        if (h.y > f.y && h.y > e.y) {
            c = {x: h.x, y: h.y + b.height}
        } else {
            c = {x: h.x, y: h.y - b.height}
        }
        return c
    }, _calculateLine: function (p, n, m, f, e, u, b) {
        var t = this.seriesGroups[p.groupIndex];
        var l = undefined;
        if (t.polar == true || t.spider == true) {
            l = this._getPolarAxisCoords(p.groupIndex, this._plotRect)
        }
        var q = "";
        var r = n.length;
        if (!u && m.length == 0) {
            r = Math.round(r * e)
        }
        var h = NaN;
        for (var s = 0; s < r + 1 && s < n.length; s++) {
            if (s > 0) {
                q += " "
            }
            var j = n[s].y;
            var k = n[s].x;
            var c = !u ? j : f;
            var d = k;
            if (m && m.length > s) {
                c = m[s].y;
                d = m[s].x;
                if (isNaN(c) || isNaN(d)) {
                    c = j;
                    d = k
                }
            }
            h = d;
            if (r <= n.length && s > 0 && s == r) {
                d = n[s - 1].x;
                c = n[s - 1].y
            }
            if (b) {
                k = a.jqx._ptrnd((k - d) * e + d);
                j = a.jqx._ptrnd((j - c) * e + c)
            } else {
                k = a.jqx._ptrnd((k - d) * e + d);
                j = a.jqx._ptrnd((j - c) * e + c)
            }
            if (l) {
                var o = this._toPolarCoord(l, this._plotRect, k, j);
                k = o.x;
                j = o.y
            }
            q += k + "," + j;
            if (n.length == 1 && !u) {
                q += " " + (k + 2) + "," + (j + 2)
            }
        }
        return q
    }, _buildLineCmd: function (k, i, f, o, n, b, p, m, q, d, j) {
        var e = k;
        if (m && !q && !i) {
            var c = j ? p + "," + f : f + "," + p;
            var h = j ? p + "," + o : o + "," + p;
            e = c + " " + k + " " + h
        }
        if (d) {
            e = this._getBezierPoints(e)
        }
        var l = e.split(" ");
        var g = l[0].replace("C", "");
        if (m && !q) {
            if (!i) {
                e = "M " + c + " L " + g + " " + e + " Z"
            } else {
                e = "M " + g + " L " + g + (d ? "" : (" L " + g + " ")) + e + " Z"
            }
        } else {
            if (d) {
                e = "M " + g + " " + e
            } else {
                e = "M " + g + " L " + g + " " + e
            }
        }
        if (q && m) {
            e += " Z"
        }
        return e
    }, _getSerieSettings: function (i, c) {
        var h = this.seriesGroups[i];
        var g = h.type.indexOf("area") != -1;
        var f = h.type.indexOf("line") != -1;
        var b = this._getColors(i, c, undefined, this._getGroupGradientType(i));
        var d = h.series[c];
        var k = d.dashStyle || h.dashStyle || "";
        var e = d.opacity || h.opacity;
        if (isNaN(e) || e < 0 || e > 1) {
            e = 1
        }
        var j = d.lineWidth;
        if (isNaN(j) && j != "auto") {
            j = h.lineWidth
        }
        if (j == "auto" || isNaN(j) || j < 0 || j > 15) {
            if (g) {
                j = 2
            } else {
                if (f) {
                    j = 3
                } else {
                    j = 1
                }
            }
        }
        return{colors: b, stroke: j, opacity: e, dashStyle: k}
    }, getItemColor: function (f, d, c) {
        var g = -1;
        for (var b = 0; b < this.seriesGroups.length; b++) {
            if (this.seriesGroups[b] == f) {
                g = b;
                break
            }
        }
        if (g == -1) {
            return"#000000"
        }
        var e = -1;
        for (var b = 0; b < this.seriesGroups[g].series.length; b++) {
            if (this.seriesGroups[g].series[b] == d) {
                e = b;
                break
            }
        }
        if (e == -1) {
            return"#000000"
        }
        return this._getColors(g, e, c)
    }, _getColors: function (m, j, h, d) {
        var k = this.seriesGroups[m];
        if (k.type != "pie" && k.type != "donut") {
            h = undefined
        }
        var i = k.series[j].useGradient || k.useGradient;
        if (i == undefined) {
            i = true
        }
        var b = {};
        if (isNaN(h)) {
            b = this._getSeriesColors(m, j)
        } else {
            var c = this._getDataLen(m);
            color = this._getColor(k.series[j].colorScheme || k.colorScheme || this.colorScheme, j * c + h, m, j);
            b.fillColor = color;
            b.fillColorSelected = a.jqx._adjustColor(color, 1.1);
            b.lineColor = b.symbolColor = a.jqx._adjustColor(color, 0.9);
            b.lineColorSelected = b.symbolColorSelected = a.jqx._adjustColor(color, 0.9)
        }
        var g = [
            [0, 1.5],
            [100, 1]
        ];
        var e = [
            [0, 1],
            [25, 1.1],
            [50, 1.5],
            [100, 1]
        ];
        var n = [
            [0, 1.3],
            [90, 1.2],
            [100, 1]
        ];
        if (i) {
            if (d == "verticalLinearGradient") {
                b.fillColor = this.renderer._toLinearGradient(b.fillColor, true, g);
                b.fillColorSelected = this.renderer._toLinearGradient(b.fillColorSelected, true, g)
            } else {
                if (d == "horizontalLinearGradient") {
                    b.fillColor = this.renderer._toLinearGradient(b.fillColor, false, e);
                    b.fillColorSelected = this.renderer._toLinearGradient(b.fillColorSelected, false, e)
                } else {
                    if (d == "radialGradient") {
                        var f = undefined;
                        var l = g;
                        if ((k.type == "pie" || k.type == "donut" || k.polar) && h != undefined && this._renderData[m] && this._renderData[m].offsets[j]) {
                            f = this._renderData[m].offsets[j][h];
                            l = n
                        }
                        b.fillColor = this.renderer._toRadialGradient(b.fillColor, l, f);
                        b.fillColorSelected = this.renderer._toRadialGradient(b.fillColorSelected, l, f)
                    }
                }
            }
        }
        return b
    }, _installHandlers: function (d, j, i, c) {
        var b = this;
        var h = this.seriesGroups[j];
        var e = this.seriesGroups[j].series[i];
        var f = h.type.indexOf("line") != -1 || h.type.indexOf("area") != -1;
        if (!f) {
            this.renderer.addHandler(d, "mousemove", function (k) {
                k.preventDefault();
                var g = k.pageX || k.clientX || k.screenX;
                var m = k.pageY || k.clientY || k.screenY;
                var l = b.host.offset();
                g -= l.left;
                m -= l.top;
                if (b._mouseX == g && b._mouseY == m) {
                    return
                }
                if (b._toolTipElement) {
                    if (b._toolTipElement.gidx == j && b._toolTipElement.sidx == i && b._toolTipElement.iidx == c) {
                        return
                    }
                }
                b._startTooltipTimer(j, i, c)
            })
        }
        this.renderer.addHandler(d, "mouseover", function (g) {
            g.preventDefault();
            b._select(d, j, i, c);
            if (f) {
                return
            }
            if (isNaN(c)) {
                return
            }
            b._raiseItemEvent("mouseover", h, e, c)
        });
        this.renderer.addHandler(d, "mouseout", function (g) {
            g.preventDefault();
            if (c != undefined) {
                b._cancelTooltipTimer()
            }
            if (f) {
                return
            }
            b._unselect();
            if (isNaN(c)) {
                return
            }
            b._raiseItemEvent("mouseout", h, e, c)
        });
        this.renderer.addHandler(d, "click", function (g) {
            g.preventDefault();
            if (f) {
                return
            }
            if (h.type.indexOf("column") != -1) {
                b._unselect()
            }
            if (isNaN(c)) {
                return
            }
            b._raiseItemEvent("click", h, e, c)
        })
    }, _getHorizontalOffset: function (A, s, k, j) {
        var c = this._plotRect;
        var h = this._getDataLen(A);
        if (h == 0) {
            return{index: undefined, value: k}
        }
        var p = this._calcGroupOffsets(A, this._plotRect);
        if (p.xoffsets.length == 0) {
            return{index: undefined, value: undefined}
        }
        var n = k;
        var m = j;
        var w = this.seriesGroups[A];
        var l;
        if (w.polar || w.spider) {
            l = this._getPolarAxisCoords(A, c)
        }
        if (w.orientation == "horizontal" && !l) {
            var z = n;
            n = m;
            m = z
        }
        var e = this._getCategoryAxis(A).flip == true;
        var b, o, v, f;
        for (var t = p.xoffsets.first; t <= p.xoffsets.last; t++) {
            var u = p.xoffsets.data[t];
            var d = p.offsets[s][t].to;
            var q = 0;
            if (l) {
                var r = this._toPolarCoord(l, c, u + c.x, d);
                u = r.x;
                d = r.y;
                q = a.jqx._ptdist(n, m, u, d)
            } else {
                u += c.x;
                d += c.y;
                q = Math.abs(n - u)
            }
            if (isNaN(b) || b > q) {
                b = q;
                o = t;
                v = u;
                f = d
            }
        }
        return{index: o, value: p.xoffsets.data[o], polarAxisCoords: l, x: v, y: f}
    }, onmousemove: function (l, j) {
        if (this._mouseX == l && this._mouseY == j) {
            return
        }
        this._mouseX = l;
        this._mouseY = j;
        if (!this._selected) {
            return
        }
        var b = this._plotRect;
        var h = this._paddedRect;
        if (l < h.x || l > h.x + h.width || j < h.y || j > h.y + h.height) {
            this._unselect();
            return
        }
        var w = this._selected.group;
        var t = this.seriesGroups[w];
        var o = t.series[this._selected.series];
        var d = t.orientation == "horizontal";
        var b = this._plotRect;
        if (t.type.indexOf("line") != -1 || t.type.indexOf("area") != -1) {
            var f = this._getHorizontalOffset(w, this._selected.series, l, j);
            var r = f.index;
            if (r == undefined) {
                return
            }
            if (this._selected.item != r) {
                if (this._selected.item) {
                    this._raiseItemEvent("mouseout", t, o, this._selected.item)
                }
                this._selected.item = r;
                this._raiseItemEvent("mouseover", t, o, r)
            }
            var n = this._getSymbol(this._selected.group, this._selected.series);
            if (n == "none") {
                n = "circle"
            }
            var p = this._calcGroupOffsets(w, b);
            var c = p.offsets[this._selected.series][r].to;
            var q = c;
            if (t.type.indexOf("range") != -1) {
                q = p.offsets[this._selected.series][r].from
            }
            var m = d ? l : j;
            if (!isNaN(q) && Math.abs(m - q) < Math.abs(m - c)) {
                j = q
            } else {
                j = c
            }
            if (isNaN(j)) {
                return
            }
            l = f.value;
            if (d) {
                var u = l;
                l = j;
                j = u + b.y
            } else {
                l += b.x
            }
            if (f.polarAxisCoords) {
                l = f.x;
                j = f.y
            }
            j = a.jqx._ptrnd(j);
            l = a.jqx._ptrnd(l);
            if (this._pointMarker && this._pointMarker.element) {
                this.renderer.removeElement(this._pointMarker.element);
                this._pointMarker.element = undefined
            }
            if (isNaN(l) || isNaN(j)) {
                return
            }
            var k = this._getSeriesColors(this._selected.group, this._selected.series);
            var e = o.opacity;
            if (isNaN(e) || e < 0 || e > 1) {
                e = t.opacity
            }
            if (isNaN(e) || e < 0 || e > 1) {
                e = 1
            }
            var v = o.symbolSizeSelected;
            if (isNaN(v)) {
                v = o.symbolSize
            }
            if (isNaN(v) || v > 10 || v < 0) {
                v = t.symbolSize
            }
            if (isNaN(v) || v > 10 || v < 0) {
                v = 6
            }
            this._pointMarker = {type: n, x: l, y: j, gidx: w, sidx: this._selected.series, iidx: r};
            this._pointMarker.element = this._drawSymbol(n, l, j, k.fillColorSymbolSelected, k.lineColorSymbolSelected, 1, e, v);
            this._startTooltipTimer(w, this._selected.series, r)
        }
    }, _drawSymbol: function (g, i, h, j, k, d, e, m) {
        var c;
        var f = m || 6;
        var b = f / 2;
        switch (g) {
            case"none":
                return undefined;
            case"circle":
                c = this.renderer.circle(i, h, f / 2);
                break;
            case"square":
                f = f - 1;
                b = f / 2;
                c = this.renderer.rect(i - b, h - b, f, f);
                break;
            case"diamond":
                var l = "M " + (i - b) + "," + (h) + " L" + (i) + "," + (h - b) + " L" + (i + b) + "," + (h) + " L" + (i) + "," + (h + b) + " Z";
                c = this.renderer.path(l);
                break;
            case"triangle_up":
                var l = "M " + (i - b) + "," + (h + b) + " L " + (i + b) + "," + (h + b) + " L " + (i) + "," + (h - b) + " Z";
                c = this.renderer.path(l);
                break;
            case"triangle_down":
                var l = "M " + (i - b) + "," + (h - b) + " L " + (i) + "," + (h + b) + " L " + (i + b) + "," + (h - b) + " Z";
                c = this.renderer.path(l);
                break;
            case"triangle_left":
                var l = "M " + (i - b) + "," + (h) + " L " + (i + b) + "," + (h + b) + " L " + (i + b) + "," + (h - b) + " Z";
                c = this.renderer.path(l);
                break;
            case"triangle_right":
                var l = "M " + (i - b) + "," + (h - b) + " L " + (i - b) + "," + (h + b) + " L " + (i + b) + "," + (h) + " Z";
                c = this.renderer.path(l);
                break;
            default:
                c = this.renderer.circle(i, h, f)
        }
        this.renderer.attr(c, {fill: j, stroke: k, "stroke-width": d, "stroke-opacity": e, "fill-opacity": e});
        return c
    }, _getSymbol: function (f, b) {
        var c = ["circle", "square", "diamond", "triangle_up", "triangle_down", "triangle_left", "triangle_right"];
        var e = this.seriesGroups[f];
        var d = e.series[b];
        var h = undefined;
        if (d.symbolType != undefined) {
            h = d.symbolType
        }
        if (h == undefined) {
            h = e.symbolType
        }
        if (h == "default") {
            return c[b % c.length]
        } else {
            if (h != undefined) {
                return h
            }
        }
        return"none"
    }, _startTooltipTimer: function (h, f, d) {
        this._cancelTooltipTimer();
        var b = this;
        var e = b.seriesGroups[h];
        var c = this.toolTipShowDelay || this.toolTipDelay;
        if (isNaN(c) || c > 10000 || c < 0) {
            c = 500
        }
        if (this._toolTipElement || (true == this.enableCrosshairs && false == this.showToolTips)) {
            c = 0
        }
        clearTimeout(this._tttimerHide);
        this._tttimer = setTimeout(function () {
            b._showToolTip(b._mouseX, b._mouseY - 3, h, f, d);
            var g = b.toolTipHideDelay;
            if (isNaN(g)) {
                g = 4000
            }
            b._tttimerHide = setTimeout(function () {
                b._hideToolTip()
            }, g)
        }, c)
    }, _cancelTooltipTimer: function () {
        clearTimeout(this._tttimer)
    }, _getGroupGradientType: function (c) {
        var b = this.seriesGroups[c];
        if (b.type.indexOf("area") != -1) {
            return b.orientation == "horizontal" ? "horizontalLinearGradient" : "verticalLinearGradient"
        } else {
            if (b.type.indexOf("column") != -1) {
                if (b.polar) {
                    return"radialGradient"
                }
                return b.orientation == "horizontal" ? "verticalLinearGradient" : "horizontalLinearGradient"
            } else {
                if (b.type.indexOf("scatter") != -1 || b.type.indexOf("bubble") != -1 || b.type.indexOf("pie") != -1 || b.type.indexOf("donut") != -1) {
                    return"radialGradient"
                }
            }
        }
        return undefined
    }, _select: function (d, i, h, c) {
        if (this._selected && this._selected.element != d) {
            this._unselect()
        }
        this._selected = {element: d, group: i, series: h, item: c};
        var f = this.seriesGroups[i];
        var b = this._getColors(i, h, c, this._getGroupGradientType(i));
        if (f.type.indexOf("line") != -1 && f.type.indexOf("area") == -1) {
            b.fillColorSelected = "none"
        }
        var e = this._getSerieSettings(i, h, c);
        this.renderer.attr(d, {stroke: b.lineColorSelected, fill: b.fillColorSelected, "stroke-width": e.stroke + 0})
    }, _unselect: function () {
        if (this._selected) {
            var i = this._selected.group;
            var h = this._selected.series;
            var c = this._selected.item;
            var f = this.seriesGroups[i];
            var e = f.series[h];
            var b = this._getColors(i, h, c, this._getGroupGradientType(i));
            if (f.type.indexOf("line") != -1 && f.type.indexOf("area") == -1) {
                b.fillColor = "none"
            }
            var d = this._getSerieSettings(i, h, c);
            this.renderer.attr(this._selected.element, {stroke: b.lineColor, fill: b.fillColor, "stroke-width": d.stroke});
            if ((f.type.indexOf("line") != -1 || f.type.indexOf("area") != -1) && !isNaN(c)) {
                this._raiseItemEvent("mouseout", f, e, c)
            }
            this._selected = undefined
        }
        if (this._pointMarker) {
            if (this._pointMarker.element) {
                this.renderer.removeElement(this._pointMarker.element);
                this._pointMarker.element = undefined
            }
            this._pointMarker = undefined;
            this._hideCrosshairs()
        }
    }, _raiseItemEvent: function (f, g, e, c) {
        var d = e[f] || g[f];
        var h = 0;
        for (; h < this.seriesGroups.length; h++) {
            if (this.seriesGroups[h] == g) {
                break
            }
        }
        if (h == this.seriesGroups.length) {
            return
        }
        var b = {event: f, seriesGroup: g, serie: e, elementIndex: c, elementValue: this._getDataValue(c, e.dataField, h)};
        if (d && a.isFunction(d)) {
            d(b)
        }
        this._raiseEvent(f, b)
    }, _raiseEvent: function (d, c) {
        var e = new jQuery.Event(d);
        e.owner = this;
        e.args = c;
        var b = this.host.trigger(e);
        return b
    }, _calcInterval: function (d, j, h) {
        var m = Math.abs(j - d);
        var k = m / h;
        var f = [1, 2, 3, 4, 5, 10, 15, 20, 25, 50, 100];
        var b = [0.5, 0.25, 0.125, 0.1];
        var c = 0.1;
        var g = f;
        if (k < 1) {
            g = b;
            c = 10
        }
        var l = 0;
        do {
            l = 0;
            if (k >= 1) {
                c *= 10
            } else {
                c /= 10
            }
            for (var e = 1; e < g.length; e++) {
                if (Math.abs(g[l] * c - k) > Math.abs(g[e] * c - k)) {
                    l = e
                } else {
                    break
                }
            }
        } while (l == g.length - 1);
        return g[l] * c
    }, _renderDataDeepCopy: function () {
        if (!this._renderData || this._isToggleRefresh) {
            return
        }
        var d = this._elementRenderInfo = [];
        for (var h = 0; h < this._renderData.length; h++) {
            var c = this._getCategoryAxis(h).dataField;
            while (d.length <= h) {
                d.push({})
            }
            var b = d[h];
            var f = this._renderData[h];
            if (!f.offsets) {
                continue
            }
            if (f.valueAxis) {
                b.valueAxis = {itemOffsets: {}};
                for (var k in f.valueAxis.itemOffsets) {
                    b.valueAxis.itemOffsets[k] = f.valueAxis.itemOffsets[k]
                }
            }
            if (f.categoryAxis) {
                b.categoryAxis = {itemOffsets: {}};
                for (var k in f.categoryAxis.itemOffsets) {
                    b.categoryAxis.itemOffsets[k] = f.categoryAxis.itemOffsets[k]
                }
            }
            b.series = [];
            var g = b.series;
            var j = this.seriesGroups[h].type;
            var m = j.indexOf("pie") != -1 || j.indexOf("donut") != -1;
            for (var n = 0; n < f.offsets.length; n++) {
                g.push({});
                for (var e = 0; e < f.offsets[n].length; e++) {
                    if (!m) {
                        g[n][f.xoffsets.xvalues[e]] = {value: f.offsets[n][e].value, valueFrom: f.offsets[n][e].valueFrom, valueRadius: f.offsets[n][e].valueRadius, xoffset: f.xoffsets.data[e], from: f.offsets[n][e].from, to: f.offsets[n][e].to}
                    } else {
                        var l = f.offsets[n][e];
                        g[n][l.displayValue] = {value: l.value, x: l.x, y: l.y, fromAngle: l.fromAngle, toAngle: l.toAngle}
                    }
                }
            }
        }
    }, _calcGroupOffsets: function (f, I) {
        var u = this.seriesGroups[f];
        while (this._renderData.length < f + 1) {
            this._renderData.push({})
        }
        if (this._renderData[f] != null && this._renderData[f].offsets != undefined) {
            return this._renderData[f]
        }
        if (u.type.indexOf("pie") != -1 || u.type.indexOf("donut") != -1) {
            return this._calcPieSeriesGroupOffsets(f, I)
        }
        if (!u.valueAxis || !u.series || u.series.length == 0) {
            return this._renderData[f]
        }
        var v = u.valueAxis.flip == true;
        var L = u.valueAxis.logarithmicScale == true;
        var K = u.valueAxis.logarithmicScaleBase || 10;
        var Q = new Array();
        var C = u.type.indexOf("stacked") != -1;
        var c = C && u.type.indexOf("100") != -1;
        var H = u.type.indexOf("range") != -1;
        var o = this._getDataLen(f);
        var n = u.baselineValue || u.valueAxis.baselineValue || 0;
        if (H) {
            n = 0
        }
        var aa = this._stats.seriesGroups[f];
        if (!aa || !aa.isValid) {
            return
        }
        if (n > aa.max) {
            n = aa.max
        }
        if (n < aa.min) {
            n = aa.min
        }
        var m = (c || L) ? aa.maxRange : aa.max - aa.min;
        var ae = aa.min;
        var A = aa.max;
        var J = I.height / (L ? aa.intervals : m);
        var ac = 0;
        if (c) {
            if (ae * A < 0) {
                m /= 2;
                ac = -(m + n) * J
            } else {
                ac = -n * J
            }
        } else {
            ac = -(n - ae) * J
        }
        if (v) {
            ac = I.y - ac
        } else {
            ac += I.y + I.height
        }
        var ab = new Array();
        var X = new Array();
        var P = new Array();
        var ad, E;
        if (L) {
            ad = a.jqx.log(A, K) - a.jqx.log(n, K);
            if (C) {
                ad = aa.intervals;
                n = c ? 0 : ae
            }
            E = aa.intervals - ad;
            if (!v) {
                ac = I.y + ad / aa.intervals * I.height
            }
        }
        ac = a.jqx._ptrnd(ac);
        var b = (ae * A < 0) ? I.height / 2 : I.height;
        var g = [];
        var z = [];
        if (u.bands) {
            for (var V = 0; V < u.bands.length; V++) {
                var r = u.bands[V].minValue;
                var ah = u.bands[V].maxValue;
                var aj = 0;
                var ai = 0;
                if (L) {
                    aj = (a.jqx.log(r, K) - a.jqx.log(n, K)) * J;
                    ai = (a.jqx.log(ah, K) - a.jqx.log(n, K)) * J
                } else {
                    aj = (r - n) * J;
                    ai = (ah - n) * J
                }
                if (this._isVML) {
                    aj = Math.round(aj);
                    ai = Math.round(ai)
                } else {
                    aj = a.jqx._ptrnd(aj) - 1;
                    ai = a.jqx._ptrnd(ai) - 1
                }
                if (v) {
                    z.push({from: ac + ai, to: ac + aj})
                } else {
                    z.push({from: ac - ai, to: ac - aj})
                }
            }
        }
        for (var V = 0; V < u.series.length; V++) {
            if (!C && L) {
                g = []
            }
            var B = u.series[V].dataField;
            var ag = u.series[V].dataFieldFrom;
            var M = u.series[V].dataFieldTo;
            var T = u.series[V].radiusDataField;
            Q.push(new Array());
            var e = this._isSerieVisible(f, V);
            for (var W = 0; W < o; W++) {
                var af = NaN;
                if (H) {
                    af = this._getDataValueAsNumber(W, ag, f);
                    if (isNaN(af)) {
                        af = n
                    }
                }
                var G = NaN;
                if (H) {
                    G = this._getDataValueAsNumber(W, M, f)
                } else {
                    G = this._getDataValueAsNumber(W, B, f)
                }
                var d = this._getDataValueAsNumber(W, T, f);
                if (!e) {
                    G = NaN
                }
                if (isNaN(G) || (L && G <= 0)) {
                    Q[V].push({from: undefined, to: undefined});
                    continue
                }
                var F = (G >= n) ? ab : X;
                var Z = J * (G - n);
                if (H) {
                    Z = J * (G - af)
                }
                if (L) {
                    while (g.length <= W) {
                        g.push({p: {value: 0, height: 0}, n: {value: 0, height: 0}})
                    }
                    var w = H ? af : n;
                    var U = G > w ? g[W].p : g[W].n;
                    U.value += G;
                    if (c) {
                        G = U.value / (aa.psums[W] + aa.nsums[W]) * 100;
                        Z = (a.jqx.log(G, K) - aa.minPow) * J
                    } else {
                        Z = a.jqx.log(U.value, K) - a.jqx.log(w, K);
                        Z *= J
                    }
                    Z -= U.height;
                    U.height += Z
                }
                var O = ac;
                if (H) {
                    var p = 0;
                    if (L) {
                        p = (a.jqx.log(af, K) - a.jqx.log(n, K)) * J
                    } else {
                        p = (af - n) * J
                    }
                    O += v ? p : -p
                }
                if (C) {
                    if (c && !L) {
                        var t = (aa.psums[W] - aa.nsums[W]);
                        if (G > n) {
                            Z = (aa.psums[W] / t) * b;
                            if (aa.psums[W] != 0) {
                                Z *= G / aa.psums[W]
                            }
                        } else {
                            Z = (aa.nsums[W] / t) * b;
                            if (aa.nsums[W] != 0) {
                                Z *= G / aa.nsums[W]
                            }
                        }
                    }
                    if (isNaN(F[W])) {
                        F[W] = O
                    }
                    O = F[W]
                }
                if (isNaN(P[W])) {
                    P[W] = 0
                }
                var Y = P[W];
                Z = Math.abs(Z);
                var R = Z;
                h_new = this._isVML ? Math.round(Z) : a.jqx._ptrnd(Z) - 1;
                if (Math.abs(Z - h_new) > 0.5) {
                    Z = Math.round(Z)
                } else {
                    Z = h_new
                }
                Y += Z - R;
                if (!C) {
                    Y = 0
                }
                if (Math.abs(Y) > 0.5) {
                    if (Y > 0) {
                        Z -= 1;
                        Y -= 1
                    } else {
                        Z += 1;
                        Y += 1
                    }
                }
                P[W] = Y;
                if (V == u.series.length - 1 && c) {
                    var s = 0;
                    for (var S = 0; S < V; S++) {
                        s += Math.abs(Q[S][W].to - Q[S][W].from)
                    }
                    s += Z;
                    if (s < b) {
                        if (Z > 0.5) {
                            Z = a.jqx._ptrnd(Z + b - s)
                        } else {
                            var S = V - 1;
                            while (S >= 0) {
                                var D = Math.abs(Q[S][W].to - Q[S][W].from);
                                if (D > 1) {
                                    if (Q[S][W].from > Q[S][W].to) {
                                        Q[S][W].from += b - s
                                    }
                                    break
                                }
                                S--
                            }
                        }
                    }
                }
                if (v) {
                    Z *= -1
                }
                var N = G < n;
                if (H) {
                    N = af > G
                }
                var l = isNaN(af) ? G : {from: af, to: G};
                if (N) {
                    F[W] += Z;
                    Q[V].push({from: O, to: O + Z, value: l, valueFrom: af, valueRadius: d})
                } else {
                    F[W] -= Z;
                    Q[V].push({from: O, to: O - Z, value: l, valueFrom: af, valueRadius: d})
                }
            }
        }
        var q = this._renderData[f];
        q.baseOffset = ac;
        q.offsets = Q;
        q.bands = z;
        q.xoffsets = this._calculateXOffsets(f, I.width);
        return this._renderData[f]
    }, _calcPieSeriesGroupOffsets: function (d, b) {
        var k = this._getDataLen(d);
        var l = this.seriesGroups[d];
        var u = this._renderData[d] = {};
        var A = u.offsets = [];
        for (var v = 0; v < l.series.length; v++) {
            var q = l.series[v];
            var m = q.initialAngle || 0;
            var t = m;
            var e = q.radius || Math.min(b.width, b.height) * 0.4;
            if (isNaN(e)) {
                e = 1
            }
            var j = q.innerRadius || 0;
            if (isNaN(j) || j >= e) {
                j = 0
            }
            var c = q.centerOffset || 0;
            var E = a.jqx.getNum([q.offsetX, l.offsetX, b.width / 2]);
            var D = a.jqx.getNum([q.offsetY, l.offsetY, b.height / 2]);
            A.push([]);
            var f = 0;
            var g = 0;
            for (var z = 0; z < k; z++) {
                var F = this._getDataValueAsNumber(z, q.dataField, d);
                if (isNaN(F)) {
                    continue
                }
                if (F > 0) {
                    f += F
                } else {
                    g += F
                }
            }
            var p = f - g;
            if (p == 0) {
                p = 1
            }
            for (var z = 0; z < k; z++) {
                var F = this._getDataValueAsNumber(z, q.dataField, d);
                if (isNaN(F)) {
                    A[v].push({});
                    continue
                }
                var w = q.displayText || q.displayField;
                var h = this._getDataValue(z, w, d);
                if (h == undefined) {
                    h = z
                }
                var C = Math.round(Math.abs(F) / p * 360);
                if (z + 1 == k) {
                    C = 360 + m - t
                }
                var o = b.x + E;
                var n = b.y + D;
                var B = c;
                if (a.isFunction(c)) {
                    B = c({seriesIndex: v, seriesGroupIndex: d, itemIndex: z})
                }
                if (isNaN(B)) {
                    B = 0
                }
                var r = {key: d + "_" + v + "_" + z, value: F, displayValue: h, x: o, y: n, fromAngle: t, toAngle: t + C, centerOffset: B, innerRadius: j, outerRadius: e};
                A[v].push(r);
                t += C
            }
        }
        return u
    }, _isPointSeriesOnly: function () {
        for (var b = 0; b < this.seriesGroups.length; b++) {
            var c = this.seriesGroups[b];
            if (c.type.indexOf("line") == -1 && c.type.indexOf("area") == -1 && c.type.indexOf("scatter") == -1 && c.type.indexOf("bubble") == -1) {
                return false
            }
        }
        return true
    }, _alignValuesWithTicks: function (f) {
        var b = this._isPointSeriesOnly();
        var c = this.seriesGroups[f];
        var e = this._getCategoryAxis(f);
        var d = e.valuesOnTicks == undefined ? b : e.valuesOnTicks != false;
        if (f == undefined) {
            return d
        }
        if (c.valuesOnTicks == undefined) {
            return d
        }
        return c.valuesOnTicks
    }, _getYearsDiff: function (c, b) {
        return b.getFullYear() - c.getFullYear()
    }, _getMonthsDiff: function (c, b) {
        return 12 * (b.getFullYear() - c.getFullYear()) + b.getMonth() - c.getMonth()
    }, _getDateDiff: function (f, e, d, b) {
        var c = 0;
        if (d != "year" && d != "month") {
            c = e.valueOf() - f.valueOf()
        }
        switch (d) {
            case"year":
                c = this._getYearsDiff(f, e);
                break;
            case"month":
                c = this._getMonthsDiff(f, e);
                break;
            case"day":
                c /= (24 * 3600 * 1000);
                break;
            case"hour":
                c /= (3600 * 1000);
                break;
            case"minute":
                c /= (60 * 1000);
                break;
            case"second":
                c /= (1000);
                break;
            case"millisecond":
                break
        }
        if (d != "year" && d != "month" && b != false) {
            c = a.jqx._rnd(c, 1, true)
        }
        return c
    }, _getDateTimeArray: function (f, m, o, n, b) {
        var h = [];
        var j = this._getDateDiff(f, m, o);
        if (n) {
            j += b
        }
        if (o == "year") {
            var d = f.getFullYear();
            for (var g = 0; g < j; g++) {
                h.push(new Date(d, 0, 1, 0, 0, 0, 0));
                d++
            }
        } else {
            if (o == "month") {
                var k = f.getMonth();
                var l = f.getFullYear();
                for (var g = 0; g < j; g++) {
                    h.push(new Date(l, k, 1, 0, 0, 0, 0));
                    k++;
                    if (k > 11) {
                        l++;
                        k = 0
                    }
                }
            } else {
                if (o == "day") {
                    for (var g = 0; g < j; g++) {
                        var e = new Date(f.valueOf() + g * 1000 * 3600 * 24);
                        h.push(e)
                    }
                } else {
                    var c = 0;
                    switch (o) {
                        case"millisecond":
                            c = 1;
                            break;
                        case"second":
                            c = 1000;
                            break;
                        case"minute":
                            c = 60 * 1000;
                            break;
                        case"hour":
                            c = 3600 * 1000;
                            break
                    }
                    for (var g = 0; g < j; g++) {
                        var e = new Date(f.valueOf() + g * c);
                        h.push(e)
                    }
                }
            }
        }
        return h
    }, _getAsDate: function (b, c) {
        b = this._castAsDate(b);
        if (c == "month") {
            return new Date(b.getFullYear(), b.getMonth(), 1)
        }
        if (c == "year") {
            return new Date(b.getFullYear(), 0, 1)
        }
        if (c == "day") {
            return new Date(b.getFullYear(), b.getMonth(), b.getDate())
        }
        return b
    }, _getCategoryAxisStats: function (p, c) {
        var e = this._getDataLen(p);
        var m = c.type == "date" || c.type == "time";
        var l = m ? this._castAsDate(c.minValue) : this._castAsNumber(c.minValue);
        var b = m ? this._castAsDate(c.maxValue) : this._castAsNumber(c.maxValue);
        var h = l, n = b;
        if (isNaN(h) || isNaN(n)) {
            for (var j = 0; j < e; j++) {
                var o = this._getDataValue(j, c.dataField, p);
                o = m ? this._castAsDate(o) : this._castAsNumber(o);
                if (isNaN(o)) {
                    continue
                }
                if (isNaN(l)) {
                    if (o < h || isNaN(h)) {
                        h = o
                    }
                }
                if (isNaN(b)) {
                    if (o > n || isNaN(n)) {
                        n = o
                    }
                }
            }
        }
        if (m) {
            h = new Date(h);
            n = new Date(n)
        }
        if (m && !(this._isDate(h) && this._isDate(n))) {
            throw"Invalid Date values"
        }
        var g = !isNaN(c.maxValue) || !isNaN(c.minValue);
        if (g && (isNaN(n) || isNaN(h))) {
            g = false;
            throw"Invalid min/max category values"
        }
        if (!g && !m) {
            h = 0;
            n = e - 1
        }
        var f = c.baseUnit;
        var k = f == "hour" || f == "minute" || f == "second" || f == "millisecond";
        var d = c.unitInterval;
        if (isNaN(d) || d <= 0) {
            d = 1
        }
        if (k) {
            if (f == "second") {
                d *= 1000
            } else {
                if (f == "minute") {
                    d *= 60 * 1000
                } else {
                    if (f == "hour") {
                        d *= 3600 * 1000
                    }
                }
            }
        }
        return{min: h, max: n, isRange: g, isDateTime: m, isTimeUnit: k, dateTimeUnit: f, interval: d}
    }, _scaleDateTimeAxis: function (h, f) {
        var g = h.min;
        var k = h.max;
        var e = h.dateTimeUnit;
        var i = h.isTimeUnit;
        var c = h.interval;
        var l = this._getAsDate(k, e);
        var j = this._getAsDate(g, e);
        if (!i && !f) {
            if (e == "month") {
                l.setMonth(l.getMonth() + 1)
            } else {
                if (e == "year") {
                    l.setYear(l.getFullYear() + 1)
                } else {
                    l.setDate(l.getDate() + 1)
                }
            }
        }
        var b = 0;
        var d = this._getDateDiff(j, l, i ? "millisecond" : e);
        while (l <= k) {
            d = a.jqx._rnd(d, c, true);
            if (e == "month") {
                j = new Date(j.getFullYear(), j.getMonth(), 1);
                l = new Date(j);
                l.setMonth(l.getMonth() + d)
            } else {
                if (e == "year") {
                    j = new Date(j.getFullYear(), 0, 1);
                    l = new Date(j);
                    l.setYear(l.getFullYear() + d)
                } else {
                    l = new Date(g);
                    if (i) {
                        l.setTime(j.getTime() + d)
                    } else {
                        l.setDate(j.getDate() + d)
                    }
                }
            }
            if (l < k) {
                d++
            } else {
                break
            }
        }
        if (!i) {
            if (e == "day") {
                b = this._getDateDiff(j, l, "day", false)
            } else {
                b = a.jqx._rnd(this._getDateDiff(j, l, "day", false), c, true)
            }
            l = new Date(j);
            l.setDate(j.getDate() + b)
        }
        d = this._getDateDiff(j, l, i ? "millisecond" : e);
        return{min: j, max: l, rangeLength: d, daysRange: b}
    }, _calculateXOffsets: function (f, H) {
        var o = this._getCategoryAxis(f);
        var C = new Array();
        var m = new Array();
        var n = this._getDataLen(f);
        var d = this._getCategoryAxisStats(f, o);
        var A = d.min;
        var E = d.max;
        var z = d.isRange;
        var b = d.isDateTime;
        var K = d.isTimeUnit;
        var t = d.dateTimeUnit;
        var I = d.interval;
        var F = this.seriesGroups[f];
        var c = F.polar || F.spider;
        var M = NaN;
        var l = this._alignValuesWithTicks(f);
        if (z) {
            if (l) {
                M = E - A
            } else {
                M = E - A + I
            }
        } else {
            M = n - 1;
            if (!l) {
                M++
            }
        }
        if (c && l) {
            M += I
        }
        if (M == 0) {
            M = I
        }
        var J = E;
        var G = A;
        var v = 0;
        if (b) {
            var L = this._scaleDateTimeAxis(d, l);
            G = L.min;
            J = L.max;
            M = L.rangeLength;
            v = L.daysRange
        }
        var k = Math.max(1, M / I);
        var h = H / k;
        var u = f != undefined && this.seriesGroups[f].type.indexOf("column") != -1;
        var r = 0;
        if (!l && !u) {
            if (b && !K) {
                r = H / Math.max(1, v * 2)
            } else {
                r = h / 2
            }
        }
        var q = t;
        if (b) {
            q = K ? "millisecond" : "day"
        }
        var j = -1, p = -1;
        for (var D = 0; D < n; D++) {
            var w = (o.dataField === undefined) ? D : this._getDataValue(D, o.dataField, f);
            if (!z && !b) {
                C.push(a.jqx._ptrnd(r + (D - G) / M * H));
                m.push(w);
                if (j == -1) {
                    j = D
                }
                if (p == -1 || p < D) {
                    p = D
                }
                continue
            }
            w = b ? this._castAsDate(w) : this._castAsNumber(w);
            if (isNaN(w) || w < A || w > E) {
                C.push(-1);
                m.push(undefined);
                continue
            }
            var s = 0;
            if (!b || (b && K)) {
                diffFromMin = w - G;
                s = (w - G) * H / M
            } else {
                s = this._getDateDiff(G, w, t, false) * h / I;
                if (t != "day") {
                    var B = this._getDateDiff(this._getAsDate(w, t), w, q, false);
                    s += B / v * H
                }
            }
            s = a.jqx._ptrnd(r + s);
            C.push(s);
            m.push(w);
            if (j == -1) {
                j = D
            }
            if (p == -1 || p < D) {
                p = D
            }
        }
        if (o.flip == true) {
            C.reverse()
        }
        if (K) {
            M = this._getDateDiff(G, J, o.baseUnit);
            M = a.jqx._rnd(M, 1, false)
        }
        var e = h;
        k = Math.max(1, M);
        h = H / k;
        return{data: C, xvalues: m, first: j, last: p, length: p == -1 ? 0 : p - j + 1, itemWidth: h, intervalWidth: e, rangeLength: M, min: G, max: J, customRange: z}
    }, _getCategoryAxis: function (b) {
        if (b == undefined || this.seriesGroups.length <= b) {
            return this.categoryAxis
        }
        return this.seriesGroups[b].categoryAxis || this.categoryAxis
    }, _isGreyScale: function (e, b) {
        var d = this.seriesGroups[e];
        var c = d.series[b];
        if (c.greyScale == true) {
            return true
        } else {
            if (c.greyScale == false) {
                return false
            }
        }
        if (d.greyScale == true) {
            return true
        } else {
            if (d.greyScale == false) {
                return false
            }
        }
        return this.greyScale == true
    }, _getSeriesColors: function (e, c) {
        var b = this._getSeriesColorsInternal(e, c);
        if (this._isGreyScale(e, c)) {
            for (var d in b) {
                b[d] = a.jqx.toGreyScale(b[d])
            }
        }
        return b
    }, _getSeriesColorsInternal: function (u, h) {
        var l = this.seriesGroups[u];
        var v = l.series[h];
        var r = "#222222";
        var e = "#151515";
        var n = "#222222";
        var b = "#151515";
        var d = "#222222";
        var q = "#333333";
        var o = "#222222";
        var c = "#333333";
        if (v.color || v.fillColor) {
            d = v.color || v.fillColor
        } else {
            var t = 0;
            for (var k = 0; k <= u; k++) {
                for (var f in this.seriesGroups[k].series) {
                    if (k == u && f == h) {
                        break
                    } else {
                        t++
                    }
                }
            }
            var p = this.colorScheme;
            if (l.colorScheme) {
                p = l.colorScheme;
                sidex = h
            }
            if (p == undefined || p == "") {
                p = this.colorSchemes[0].name
            }
            if (p) {
                for (var k = 0; k < this.colorSchemes.length; k++) {
                    var m = this.colorSchemes[k];
                    if (m.name == p) {
                        while (t > m.colors.length) {
                            t -= m.colors.length;
                            if (++k >= this.colorSchemes.length) {
                                k = 0
                            }
                            m = this.colorSchemes[k]
                        }
                        d = m.colors[t % m.colors.length]
                    }
                }
            }
        }
        if (v.fillColorSelected) {
            q = v.fillColorSelected
        } else {
            q = a.jqx._adjustColor(d, 1.1)
        }
        if (v.lineColor) {
            r = v.lineColor
        } else {
            r = a.jqx._adjustColor(d, 0.9)
        }
        if (v.lineColorSelected) {
            e = v.lineColorSelected
        } else {
            e = a.jqx._adjustColor(d, 0.8)
        }
        if (v.lineColorSymbol) {
            n = v.lineColorSymbol
        } else {
            n = r
        }
        if (v.lineColorSymbolSelected) {
            b = v.lineColorSymbolSelected
        } else {
            b = e
        }
        if (v.fillColorSymbol) {
            o = v.fillColorSymbol
        } else {
            o = d
        }
        if (v.fillColorSymbolSelected) {
            c = v.fillColorSymbolSelected
        } else {
            c = q
        }
        return{lineColor: r, lineColorSelected: e, fillColor: d, fillColorSelected: q, lineColorSymbol: n, lineColorSymbolSelected: b, fillColorSymbol: o, fillColorSymbolSelected: c}
    }, _getColor: function (d, f, k, h) {
        if (d == undefined || d == "") {
            d = this.colorSchemes[0].name
        }
        for (var g = 0; g < this.colorSchemes.length; g++) {
            if (d == this.colorSchemes[g].name) {
                break
            }
        }
        var e = 0;
        while (e <= f) {
            if (g == this.colorSchemes.length) {
                g = 0
            }
            var b = this.colorSchemes[g].colors.length;
            if (e + b <= f) {
                e += b;
                g++
            } else {
                var c = this.colorSchemes[g].colors[f - e];
                if (this._isGreyScale(k, h) && c.indexOf("#") == 0) {
                    c = a.jqx.toGreyScale(c)
                }
                return c
            }
        }
    }, getColorScheme: function (b) {
        for (var c in this.colorSchemes) {
            if (this.colorSchemes[c].name == b) {
                return this.colorSchemes[c].colors
            }
        }
        return undefined
    }, addColorScheme: function (c, b) {
        for (var d in this.colorSchemes) {
            if (this.colorSchemes[d].name == c) {
                this.colorSchemes[d].colors = b;
                return
            }
        }
        this.colorSchemes.push({name: c, colors: b})
    }, removeColorScheme: function (b) {
        for (var c in this.colorSchemes) {
            if (this.colorSchemes[c].name == b) {
                this.colorSchemes.splice(c, 1);
                break
            }
        }
    }, colorSchemes: [
        {name: "scheme01", colors: ["#307DD7", "#AA4643", "#89A54E", "#71588F", "#4198AF"]},
        {name: "scheme02", colors: ["#7FD13B", "#EA157A", "#FEB80A", "#00ADDC", "#738AC8"]},
        {name: "scheme03", colors: ["#E8601A", "#FF9639", "#F5BD6A", "#599994", "#115D6E"]},
        {name: "scheme04", colors: ["#D02841", "#FF7C41", "#FFC051", "#5B5F4D", "#364651"]},
        {name: "scheme05", colors: ["#25A0DA", "#309B46", "#8EBC00", "#FF7515", "#FFAE00"]},
        {name: "scheme06", colors: ["#0A3A4A", "#196674", "#33A6B2", "#9AC836", "#D0E64B"]},
        {name: "scheme07", colors: ["#CC6B32", "#FFAB48", "#FFE7AD", "#A7C9AE", "#888A63"]},
        {name: "scheme08", colors: ["#2F2933", "#01A2A6", "#29D9C2", "#BDF271", "#FFFFA6"]},
        {name: "scheme09", colors: ["#1B2B32", "#37646F", "#A3ABAF", "#E1E7E8", "#B22E2F"]},
        {name: "scheme10", colors: ["#5A4B53", "#9C3C58", "#DE2B5B", "#D86A41", "#D2A825"]},
        {name: "scheme11", colors: ["#993144", "#FFA257", "#CCA56A", "#ADA072", "#949681"]}
    ], _formatValue: function (f, i, b, h, d, c) {
        if (f == undefined) {
            return""
        }
        if (this._isObject(f) && !b) {
            return""
        }
        if (b) {
            if (!a.isFunction(b)) {
                return f.toString()
            }
            try {
                return b(f, c, d, h)
            } catch (g) {
                return g.message
            }
        }
        if (this._isNumber(f)) {
            return this._formatNumber(f, i)
        }
        if (this._isDate(f)) {
            return this._formatDate(f, i)
        }
        if (i) {
            return(i.prefix || "") + f.toString() + (i.sufix || "")
        }
        return f.toString()
    }, _getFormattedValue: function (o, f, b, c, d) {
        var j = this.seriesGroups[o];
        var q = j.series[f];
        var p = "";
        var i = c, l = d;
        if (!l) {
            l = q.formatFunction || j.formatFunction
        }
        if (!i) {
            i = q.formatSettings || j.formatSettings
        }
        if (!q.formatFunction && q.formatSettings) {
            l = undefined
        }
        if (j.type.indexOf("range") != -1) {
            var h = this._getDataValue(b, q.dataFieldFrom, o);
            var n = this._getDataValue(b, q.dataFieldTo, o);
            if (l && a.isFunction(l)) {
                try {
                    return l({from: h, to: n}, b, q, j)
                } catch (k) {
                    return k.message
                }
            }
            if (undefined != h) {
                p = this._formatValue(h, i, l, j, q, b)
            }
            if (undefined != n) {
                p += ", " + this._formatValue(n, i, l, j, q, b)
            }
        } else {
            var m = this._getDataValue(b, q.dataField, o);
            if (undefined != m) {
                p = this._formatValue(m, i, l, j, q, b)
            }
        }
        return p || ""
    }, _isNumberAsString: function (d) {
        if (typeof(d) != "string") {
            return false
        }
        d = a.trim(d);
        for (var b = 0; b < d.length; b++) {
            var c = d.charAt(b);
            if ((c >= "0" && c <= "9") || c == "," || c == ".") {
                continue
            }
            if (c == "-" && b == 0) {
                continue
            }
            if ((c == "(" && b == 0) || (c == ")" && b == d.length - 1)) {
                continue
            }
            return false
        }
        return true
    }, _castAsDate: function (c) {
        if (c instanceof Date && !isNaN(c)) {
            return c
        }
        if (typeof(c) == "string") {
            var b = new Date(c);
            if (isNaN(b)) {
                b = this._parseISO8601Date(c)
            }
            if (b != undefined && !isNaN(b)) {
                return b
            }
        }
        return undefined
    }, _parseISO8601Date: function (g) {
        var k = g.split(" ");
        if (k.length < 0) {
            return NaN
        }
        var b = k[0].split("-");
        var c = k.length == 2 ? k[1].split(":") : "";
        var f = b[0];
        var h = b.length > 1 ? b[1] - 1 : 0;
        var i = b.length > 2 ? b[2] : 1;
        var d = c[1];
        var e = c.length > 1 ? c[1] : 0;
        var d = c.length > 2 ? c[2] : 0;
        var j = c.length > 3 ? c[3] : 0;
        return new Date(f, h, i, d, e, j)
    }, _castAsNumber: function (c) {
        if (c instanceof Date && !isNaN(c)) {
            return c.valueOf()
        }
        if (typeof(c) == "string") {
            if (this._isNumber(c)) {
                c = parseFloat(c)
            } else {
                var b = new Date(c);
                if (b != undefined) {
                    c = b.valueOf()
                }
            }
        }
        return c
    }, _isNumber: function (b) {
        if (typeof(b) == "string") {
            if (this._isNumberAsString(b)) {
                b = parseFloat(b)
            }
        }
        return typeof b === "number" && isFinite(b)
    }, _isDate: function (b) {
        return b instanceof Date
    }, _isBoolean: function (b) {
        return typeof b === "boolean"
    }, _isObject: function (b) {
        return(b && (typeof b === "object" || a.isFunction(b))) || false
    }, _formatDate: function (c, b) {
        return c.toString()
    }, _formatNumber: function (n, e) {
        if (!this._isNumber(n)) {
            return n
        }
        e = e || {};
        var q = e.decimalSeparator || ".";
        var o = e.thousandsSeparator || "";
        var m = e.prefix || "";
        var p = e.sufix || "";
        var h = e.decimalPlaces;
        if (isNaN(h)) {
            h = ((n * 100 != parseInt(n) * 100) ? 2 : 0)
        }
        var l = e.negativeWithBrackets || false;
        var g = (n < 0);
        if (g && l) {
            n *= -1
        }
        var d = n.toString();
        var b;
        var k = Math.pow(10, h);
        d = (Math.round(n * k) / k).toString();
        if (isNaN(d)) {
            d = ""
        }
        b = d.lastIndexOf(".");
        if (h > 0) {
            if (b < 0) {
                d += q;
                b = d.length - 1
            } else {
                if (q !== ".") {
                    d = d.replace(".", q)
                }
            }
            while ((d.length - 1 - b) < h) {
                d += "0"
            }
        }
        b = d.lastIndexOf(q);
        b = (b > -1) ? b : d.length;
        var f = d.substring(b);
        var c = 0;
        for (var j = b; j > 0; j--, c++) {
            if ((c % 3 === 0) && (j !== b) && (!g || (j > 1) || (g && l))) {
                f = o + f
            }
            f = d.charAt(j - 1) + f
        }
        d = f;
        if (g && l) {
            d = "(" + d + ")"
        }
        return m + d + p
    }, _defaultNumberFormat: {prefix: "", sufix: "", decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2, negativeWithBrackets: false}, _getBezierPoints: function (h) {
        var m = [];
        var j = h.split(" ");
        for (var g = 0; g < j.length; g++) {
            var n = j[g].split(",");
            m.push({x: parseFloat(n[0]), y: parseFloat(n[1])})
        }
        var o = "";
        if (m.length < 3) {
            for (var g = 0; g < m.length; g++) {
                o += (g > 0 ? " " : "") + m[g].x + "," + m[g].y
            }
        } else {
            for (var g = 0; g < m.length - 1; g++) {
                var c = [];
                if (0 == g) {
                    c.push(m[g]);
                    c.push(m[g]);
                    c.push(m[g + 1]);
                    c.push(m[g + 2])
                } else {
                    if (m.length - 2 == g) {
                        c.push(m[g - 1]);
                        c.push(m[g]);
                        c.push(m[g + 1]);
                        c.push(m[g + 1])
                    } else {
                        c.push(m[g - 1]);
                        c.push(m[g]);
                        c.push(m[g + 1]);
                        c.push(m[g + 2])
                    }
                }
                var e = [];
                var k = g > 3 ? 9 : 5;
                var l = g == 0 ? 81 : k;
                var f = {x: ((-c[0].x + l * c[1].x + c[2].x) / l), y: ((-c[0].y + l * c[1].y + c[2].y) / l)};
                if (g == 0) {
                    l = k
                }
                var d = {x: ((c[1].x + l * c[2].x - c[3].x) / l), y: ((c[1].y + l * c[2].y - c[3].y) / l)};
                e.push({x: c[1].x, y: c[1].y});
                e.push(f);
                e.push(d);
                e.push({x: c[2].x, y: c[2].y});
                o += "C" + a.jqx._ptrnd(e[1].x) + "," + a.jqx._ptrnd(e[1].y) + " " + a.jqx._ptrnd(e[2].x) + "," + a.jqx._ptrnd(e[2].y) + " " + a.jqx._ptrnd(e[3].x) + "," + a.jqx._ptrnd(e[3].y) + " "
            }
        }
        return o
    }, _animTickInt: 50, _createAnimationGroup: function (b) {
        if (!this._animGroups) {
            this._animGroups = {}
        }
        this._animGroups[b] = {animations: [], startTick: NaN}
    }, _startAnimation: function (c) {
        var e = new Date();
        var b = e.getTime();
        this._animGroups[c].startTick = b;
        this._runAnimation();
        this._enableAnimTimer()
    }, _enqueueAnimation: function (e, d, c, g, f, b, h) {
        if (g < 0) {
            g = 0
        }
        if (h == undefined) {
            h = "easeInOutSine"
        }
        this._animGroups[e].animations.push({key: d, properties: c, duration: g, fn: f, context: b, easing: h})
    }, _stopAnimations: function () {
        clearTimeout(this._animtimer);
        this._animtimer = undefined;
        this._animGroups = undefined
    }, _enableAnimTimer: function () {
        if (!this._animtimer) {
            var b = this;
            this._animtimer = setTimeout(function () {
                b._runAnimation()
            }, this._animTickInt)
        }
    }, _runAnimation: function () {
        if (this._animGroups) {
            var s = new Date();
            var h = s.getTime();
            var o = {};
            for (var l in this._animGroups) {
                var r = this._animGroups[l].animations;
                var m = this._animGroups[l].startTick;
                var g = 0;
                for (var n = 0; n < r.length; n++) {
                    var t = r[n];
                    var b = (h - m);
                    if (t.duration > g) {
                        g = t.duration
                    }
                    var q = t.duration > 0 ? b / t.duration : 1;
                    var k = q;
                    if (t.easing && t.duration != 0) {
                        k = jQuery.easing[t.easing](q, b, 0, 1, t.duration)
                    }
                    if (q > 1) {
                        q = 1;
                        k = 1
                    }
                    if (t.fn) {
                        t.fn(t.key, t.context, k);
                        continue
                    }
                    var f = {};
                    for (var l = 0; l < t.properties.length; l++) {
                        var c = t.properties[l];
                        var e = 0;
                        if (q == 1) {
                            e = c.to
                        } else {
                            e = easeParecent * (c.to - c.from) + c.from
                        }
                        f[c.key] = e
                    }
                    this.renderer.attr(t.key, f)
                }
                if (m + g > h) {
                    o[l] = ({startTick: m, animations: r})
                }
            }
            this._animGroups = o;
            if (this.renderer instanceof a.jqx.HTML5Renderer) {
                this.renderer.refresh()
            }
        }
        this._animtimer = null;
        for (var l in this._animGroups) {
            this._enableAnimTimer();
            break
        }
    }});
    a.jqx.toGreyScale = function (b) {
        if (b.indexOf("#") == -1) {
            return b
        }
        var c = a.jqx.cssToRgb(b);
        c[0] = c[1] = c[2] = Math.round(0.3 * c[0] + 0.59 * c[1] + 0.11 * c[2]);
        var d = a.jqx.rgbToHex(c[0], c[1], c[2]);
        return"#" + d[0] + d[1] + d[2]
    }, a.jqx._adjustColor = function (d, b) {
        if (d.indexOf("#") == -1) {
            return d
        }
        var e = a.jqx.cssToRgb(d);
        var d = "#";
        for (var f = 0; f < 3; f++) {
            var g = Math.round(b * e[f]);
            if (g > 255) {
                g = 255
            } else {
                if (g <= 0) {
                    g = 0
                }
            }
            g = a.jqx.decToHex(g);
            if (g.toString().length == 1) {
                d += "0"
            }
            d += g
        }
        return d.toUpperCase()
    };
    a.jqx.decToHex = function (b) {
        return b.toString(16)
    }, a.jqx.hexToDec = function (b) {
        return parseInt(b, 16)
    };
    a.jqx.rgbToHex = function (e, d, c) {
        return[a.jqx.decToHex(e), a.jqx.decToHex(d), a.jqx.decToHex(c)]
    };
    a.jqx.hexToRgb = function (c, d, b) {
        return[a.jqx.hexToDec(c), a.jqx.hexToDec(d), a.jqx.hexToDec(b)]
    };
    a.jqx.cssToRgb = function (b) {
        if (b.indexOf("rgb") <= -1) {
            return a.jqx.hexToRgb(b.substring(1, 3), b.substring(3, 5), b.substring(5, 7))
        }
        return b.substring(4, b.length - 1).split(",")
    };
    a.jqx.swap = function (b, d) {
        var c = b;
        b = d;
        d = c
    };
    a.jqx.getNum = function (b) {
        if (!a.isArray(b)) {
            if (isNaN(b)) {
                return 0
            }
        } else {
            for (var c = 0; c < b.length; c++) {
                if (!isNaN(b[c])) {
                    return b[c]
                }
            }
        }
        return 0
    };
    a.jqx._ptdist = function (c, e, b, d) {
        return Math.sqrt((b - c) * (b - c) + (d - e) * (d - e))
    };
    a.jqx._ptrnd = function (c) {
        if (!document.createElementNS) {
            if (Math.round(c) == c) {
                return c
            }
            return a.jqx._rnd(c, 1, false, true)
        }
        var b = a.jqx._rnd(c, 0.5, false, true);
        if (Math.abs(b - Math.round(b)) != 0.5) {
            return b > c ? b - 0.5 : b + 0.5
        }
        return b
    };
    a.jqx._rup = function (c) {
        var b = Math.round(c);
        if (c > b) {
            b++
        }
        return b
    };
    a.jqx.log = function (c, b) {
        return Math.log(c) / (b ? Math.log(b) : 1)
    };
    a.jqx._mod = function (d, c) {
        var e = Math.abs(d > c ? c : d);
        var f = 1;
        if (e != 0) {
            while (e * f < 100) {
                f *= 10
            }
        }
        d = d * f;
        c = c * f;
        return(d % c) / f
    };
    a.jqx._rnd = function (d, f, e, c) {
        if (isNaN(d)) {
            return d
        }
        var b = d - ((c == true) ? d % f : a.jqx._mod(d, f));
        if (d == b) {
            return b
        }
        if (e) {
            if (d > b) {
                b += f
            }
        } else {
            if (b > d) {
                b -= f
            }
        }
        return b
    };
    a.jqx.commonRenderer = {pieSlicePath: function (j, i, g, q, z, A, d) {
        if (!q) {
            q = 1
        }
        var l = Math.abs(z - A);
        var o = l > 180 ? 1 : 0;
        if (l >= 360) {
            A = z + 359.99
        }
        var p = z * Math.PI * 2 / 360;
        var h = A * Math.PI * 2 / 360;
        var v = j, u = j, f = i, e = i;
        var m = !isNaN(g) && g > 0;
        if (m) {
            d = 0
        }
        if (d + g > 0) {
            if (d > 0) {
                var k = l / 2 + z;
                var w = k * Math.PI * 2 / 360;
                j += d * Math.cos(w);
                i -= d * Math.sin(w)
            }
            if (m) {
                var t = g;
                v = j + t * Math.cos(p);
                f = i - t * Math.sin(p);
                u = j + t * Math.cos(h);
                e = i - t * Math.sin(h)
            }
        }
        var s = j + q * Math.cos(p);
        var r = j + q * Math.cos(h);
        var c = i - q * Math.sin(p);
        var b = i - q * Math.sin(h);
        var n = "";
        if (m) {
            n = "M " + u + "," + e;
            n += " a" + g + "," + g;
            n += " 0 " + o + ",1 " + (v - u) + "," + (f - e);
            n += " L" + s + "," + c;
            n += " a" + q + "," + q;
            n += " 0 " + o + ",0 " + (r - s) + "," + (b - c)
        } else {
            n = "M " + r + "," + b;
            n += " a" + q + "," + q;
            n += " 0 " + o + ",1 " + (s - r) + "," + (c - b);
            n += " L" + j + "," + i + " Z"
        }
        return n
    }, measureText: function (o, f, g, n, l) {
        var e = l._getTextParts(o, f, g);
        var i = e.width;
        var b = e.height;
        if (false == n) {
            b /= 0.6
        }
        var c = {};
        if (isNaN(f)) {
            f = 0
        }
        if (f == 0) {
            c = {width: a.jqx._rup(i), height: a.jqx._rup(b)}
        } else {
            var k = f * Math.PI * 2 / 360;
            var d = Math.abs(Math.sin(k));
            var j = Math.abs(Math.cos(k));
            var h = Math.abs(i * d + b * j);
            var m = Math.abs(i * j + b * d);
            c = {width: a.jqx._rup(m), height: a.jqx._rup(h)}
        }
        if (n) {
            c.textPartsInfo = e
        }
        return c
    }, alignTextInRect: function (q, n, b, r, m, o, i, p, e, d) {
        var k = e * Math.PI * 2 / 360;
        var c = Math.sin(k);
        var j = Math.cos(k);
        var l = m * c;
        var h = m * j;
        if (i == "center" || i == "" || i == "undefined") {
            q = q + b / 2
        } else {
            if (i == "right") {
                q = q + b
            }
        }
        if (p == "center" || p == "" || p == "undefined") {
            n = n + r / 2
        } else {
            if (p == "bottom") {
                n += r - o / 2
            } else {
                if (p == "top") {
                    n += o / 2
                }
            }
        }
        d = d || "";
        var f = "middle";
        if (d.indexOf("top") != -1) {
            f = "top"
        } else {
            if (d.indexOf("bottom") != -1) {
                f = "bottom"
            }
        }
        var g = "center";
        if (d.indexOf("left") != -1) {
            g = "left"
        } else {
            if (d.indexOf("right") != -1) {
                g = "right"
            }
        }
        if (g == "center") {
            q -= h / 2;
            n -= l / 2
        } else {
            if (g == "right") {
                q -= h;
                n -= l
            }
        }
        if (f == "top") {
            q -= o * c;
            n += o * j
        } else {
            if (f == "middle") {
                q -= o * c / 2;
                n += o * j / 2
            }
        }
        q = a.jqx._rup(q);
        n = a.jqx._rup(n);
        return{x: q, y: n}
    }};
    a.jqx.svgRenderer = function () {
    };
    a.jqx.svgRenderer.prototype = {_svgns: "http://www.w3.org/2000/svg", init: function (f) {
        var d = "<table id=tblChart cellspacing='0' cellpadding='0' border='0' align='left' valign='top'><tr><td colspan=2 id=tdTop></td></tr><tr><td id=tdLeft></td><td class='chartContainer'></td></tr></table>";
        f.append(d);
        this.host = f;
        var b = f.find(".chartContainer");
        b[0].style.width = f.width() + "px";
        b[0].style.height = f.height() + "px";
        var h;
        try {
            var c = document.createElementNS(this._svgns, "svg");
            c.setAttribute("id", "svgChart");
            c.setAttribute("version", "1.1");
            c.setAttribute("width", "100%");
            c.setAttribute("height", "100%");
            c.setAttribute("overflow", "hidden");
            b[0].appendChild(c);
            this.canvas = c
        } catch (g) {
            return false
        }
        this._id = new Date().getTime();
        this.clear();
        this._layout();
        this._runLayoutFix();
        return true
    }, refresh: function () {
    }, _runLayoutFix: function () {
        var b = this;
        this._fixLayout()
    }, _fixLayout: function () {
        var g = a(this.canvas).position();
        var d = (parseFloat(g.left) == parseInt(g.left));
        var b = (parseFloat(g.top) == parseInt(g.top));
        if (a.jqx.browser.msie) {
            var d = true, b = true;
            var e = this.host;
            var c = 0, f = 0;
            while (e && e.position && e[0].parentNode) {
                var h = e.position();
                c += parseFloat(h.left) - parseInt(h.left);
                f += parseFloat(h.top) - parseInt(h.top);
                e = e.parent()
            }
            d = parseFloat(c) == parseInt(c);
            b = parseFloat(f) == parseInt(f)
        }
        if (!d) {
            this.host.find("#tdLeft")[0].style.width = "0.5px"
        }
        if (!b) {
            this.host.find("#tdTop")[0].style.height = "0.5px"
        }
    }, _layout: function () {
        var c = a(this.canvas).offset();
        var b = this.host.find(".chartContainer");
        this._width = Math.max(a.jqx._rup(this.host.width()) - 1, 0);
        this._height = Math.max(a.jqx._rup(this.host.height()) - 1, 0);
        b[0].style.width = this._width;
        b[0].style.height = this._height;
        this._fixLayout()
    }, getRect: function () {
        return{x: 0, y: 0, width: this._width, height: this._height}
    }, getContainer: function () {
        var b = this.host.find(".chartContainer");
        return b
    }, clear: function () {
        while (this.canvas.childElementCount > 0) {
            this.canvas.removeChild(this.canvas.firstElementChild)
        }
        this._defaultParent = undefined;
        this._defs = document.createElementNS(this._svgns, "defs");
        this._gradients = {};
        this.canvas.appendChild(this._defs)
    }, removeElement: function (d) {
        if (d != undefined) {
            try {
                while (d.firstChild) {
                    this.removeElement(d.firstChild)
                }
                if (d.parentNode) {
                    d.parentNode.removeChild(d)
                } else {
                    this.canvas.removeChild(d)
                }
            } catch (c) {
                var b = c
            }
        }
    }, _openGroups: [], beginGroup: function () {
        var b = this._activeParent();
        var c = document.createElementNS(this._svgns, "g");
        b.appendChild(c);
        this._openGroups.push(c);
        return c
    }, endGroup: function () {
        if (this._openGroups.length == 0) {
            return
        }
        this._openGroups.pop()
    }, _activeParent: function () {
        return this._openGroups.length == 0 ? this.canvas : this._openGroups[this._openGroups.length - 1]
    }, createClipRect: function (d) {
        var e = document.createElementNS(this._svgns, "clipPath");
        var b = document.createElementNS(this._svgns, "rect");
        this.attr(b, {x: d.x, y: d.y, width: d.width, height: d.height, fill: "none"});
        this._clipId = this._clipId || 0;
        e.id = "cl" + this._id + "_" + (++this._clipId).toString();
        e.appendChild(b);
        this._defs.appendChild(e);
        return e
    }, setClip: function (c, b) {
        return this.attr(c, {"clip-path": "url(#" + b.id + ")"})
    }, _clipId: 0, addHandler: function (b, d, c) {
        b["on" + d] = c
    }, shape: function (b, e) {
        var c = document.createElementNS(this._svgns, b);
        if (!c) {
            return undefined
        }
        for (var d in e) {
            c.setAttribute(d, e[d])
        }
        this._activeParent().appendChild(c);
        return c
    }, _getTextParts: function (q, g, h) {
        var f = {width: 0, height: 0, parts: []};
        var m = 0.6;
        var r = q.toString().split("<br>");
        var o = this._activeParent();
        var k = document.createElementNS(this._svgns, "text");
        this.attr(k, h);
        for (var j = 0; j < r.length; j++) {
            var c = r[j];
            var d = k.ownerDocument.createTextNode(c);
            k.appendChild(d);
            o.appendChild(k);
            var p;
            try {
                p = k.getBBox()
            } catch (n) {
                if (console && console.log) {
                    console.log(n)
                }
            }
            var l = a.jqx._rup(p.width);
            var b = a.jqx._rup(p.height * m);
            k.removeChild(d);
            f.width = Math.max(f.width, l);
            f.height += b + (j > 0 ? 4 : 0);
            f.parts.push({width: l, height: b, text: c})
        }
        o.removeChild(k);
        return f
    }, _measureText: function (e, d, c, b) {
        return a.jqx.commonRenderer.measureText(e, d, c, b, this)
    }, measureText: function (d, c, b) {
        return this._measureText(d, c, b, false)
    }, text: function (t, q, p, B, z, H, J, I, s, k, c) {
        var v = this._measureText(t, H, J, true);
        var j = v.textPartsInfo;
        var f = j.parts;
        var A;
        if (!s) {
            s = "center"
        }
        if (!k) {
            k = "center"
        }
        if (f.length > 1 || I) {
            A = this.beginGroup()
        }
        if (I) {
            var g = this.createClipRect({x: a.jqx._rup(q) - 1, y: a.jqx._rup(p) - 1, width: a.jqx._rup(B) + 2, height: a.jqx._rup(z) + 2});
            this.setClip(A, g)
        }
        var o = this._activeParent();
        var L = 0, l = 0;
        var b = 0.6;
        L = j.width;
        l = j.height;
        if (isNaN(B) || B <= 0) {
            B = L
        }
        if (isNaN(z) || z <= 0) {
            z = l
        }
        var r = B || 0;
        var G = z || 0;
        if (!H || H == 0) {
            p += l;
            if (k == "center") {
                p += (G - l) / 2
            } else {
                if (k == "bottom") {
                    p += G - l
                }
            }
            if (!B) {
                B = L
            }
            if (!z) {
                z = l
            }
            var o = this._activeParent();
            var n = 0;
            for (var F = f.length - 1; F >= 0; F--) {
                var u = document.createElementNS(this._svgns, "text");
                this.attr(u, J);
                this.attr(u, {cursor: "default"});
                var E = u.ownerDocument.createTextNode(f[F].text);
                u.appendChild(E);
                var M = q;
                var m = f[F].width;
                var e = f[F].height;
                if (s == "center") {
                    M += (r - m) / 2
                } else {
                    if (s == "right") {
                        M += (r - m)
                    }
                }
                this.attr(u, {x: a.jqx._rup(M), y: a.jqx._rup(p + n), width: a.jqx._rup(m), height: a.jqx._rup(e)});
                o.appendChild(u);
                n -= f[F].height + 4
            }
            if (A) {
                this.endGroup();
                return A
            }
            return u
        }
        var C = a.jqx.commonRenderer.alignTextInRect(q, p, B, z, L, l, s, k, H, c);
        q = C.x;
        p = C.y;
        var D = this.shape("g", {transform: "translate(" + q + "," + p + ")"});
        var d = this.shape("g", {transform: "rotate(" + H + ")"});
        D.appendChild(d);
        var n = 0;
        for (var F = f.length - 1; F >= 0; F--) {
            var K = document.createElementNS(this._svgns, "text");
            this.attr(K, J);
            this.attr(K, {cursor: "default"});
            var E = K.ownerDocument.createTextNode(f[F].text);
            K.appendChild(E);
            var M = 0;
            var m = f[F].width;
            var e = f[F].height;
            if (s == "center") {
                M += (j.width - m) / 2
            } else {
                if (s == "right") {
                    M += (j.width - m)
                }
            }
            this.attr(K, {x: a.jqx._rup(M), y: a.jqx._rup(n), width: a.jqx._rup(m), height: a.jqx._rup(e)});
            d.appendChild(K);
            n -= e + 4
        }
        o.appendChild(D);
        if (A) {
            this.endGroup()
        }
        return D
    }, line: function (d, f, c, e, g) {
        var b = this.shape("line", {x1: d, y1: f, x2: c, y2: e});
        this.attr(b, g);
        return b
    }, path: function (c, d) {
        var b = this.shape("path");
        b.setAttribute("d", c);
        if (d) {
            this.attr(b, d)
        }
        return b
    }, rect: function (b, g, c, e, f) {
        b = a.jqx._ptrnd(b);
        g = a.jqx._ptrnd(g);
        c = a.jqx._rup(c);
        e = a.jqx._rup(e);
        var d = this.shape("rect", {x: b, y: g, width: c, height: e});
        if (f) {
            this.attr(d, f)
        }
        return d
    }, circle: function (b, f, d, e) {
        var c = this.shape("circle", {cx: b, cy: f, r: d});
        if (e) {
            this.attr(c, e)
        }
        return c
    }, pieSlicePath: function (c, h, g, e, f, d, b) {
        return a.jqx.commonRenderer.pieSlicePath(c, h, g, e, f, d, b)
    }, pieslice: function (j, h, g, d, f, b, i, c) {
        var e = this.pieSlicePath(j, h, g, d, f, b, i);
        var k = this.shape("path");
        k.setAttribute("d", e);
        if (c) {
            this.attr(k, c)
        }
        return k
    }, attr: function (b, d) {
        if (!b || !d) {
            return
        }
        for (var c in d) {
            if (c == "textContent") {
                b.textContent = d[c]
            } else {
                b.setAttribute(c, d[c])
            }
        }
    }, getAttr: function (c, b) {
        return c.getAttribute(b)
    }, _gradients: {}, _toLinearGradient: function (e, g, h) {
        var c = "grd" + this._id + e.replace("#", "") + (g ? "v" : "h");
        var b = "url(#" + c + ")";
        if (this._gradients[b]) {
            return b
        }
        var d = document.createElementNS(this._svgns, "linearGradient");
        this.attr(d, {x1: "0%", y1: "0%", x2: g ? "0%" : "100%", y2: g ? "100%" : "0%", id: c});
        for (var f in h) {
            var j = document.createElementNS(this._svgns, "stop");
            var i = "stop-color:" + a.jqx._adjustColor(e, h[f][1]);
            this.attr(j, {offset: h[f][0] + "%", style: i});
            d.appendChild(j)
        }
        this._defs.appendChild(d);
        this._gradients[b] = true;
        return b
    }, _toRadialGradient: function (e, h, g) {
        var c = "grd" + this._id + e.replace("#", "") + "r" + (g != undefined ? g.key : "");
        var b = "url(#" + c + ")";
        if (this._gradients[b]) {
            return b
        }
        var d = document.createElementNS(this._svgns, "radialGradient");
        if (g == undefined) {
            this.attr(d, {cx: "50%", cy: "50%", r: "100%", fx: "50%", fy: "50%", id: c})
        } else {
            this.attr(d, {cx: g.x, cy: g.y, r: g.outerRadius, id: c, gradientUnits: "userSpaceOnUse"})
        }
        for (var f in h) {
            var j = document.createElementNS(this._svgns, "stop");
            var i = "stop-color:" + a.jqx._adjustColor(e, h[f][1]);
            this.attr(j, {offset: h[f][0] + "%", style: i});
            d.appendChild(j)
        }
        this._defs.appendChild(d);
        this._gradients[b] = true;
        return b
    }};
    a.jqx.vmlRenderer = function () {
    };
    a.jqx.vmlRenderer.prototype = {init: function (g) {
        var f = "<div class='chartContainer' style=\"position:relative;overflow:hidden;\"><div>";
        g.append(f);
        this.host = g;
        var b = g.find(".chartContainer");
        b[0].style.width = g.width() + "px";
        b[0].style.height = g.height() + "px";
        var d = true;
        try {
            for (var c = 0; c < document.namespaces.length; c++) {
                if (document.namespaces[c].name == "v" && document.namespaces[c].urn == "urn:schemas-microsoft-com:vml") {
                    d = false;
                    break
                }
            }
        } catch (h) {
            return false
        }
        if (a.jqx.browser.msie && parseInt(a.jqx.browser.version) < 9 && (document.childNodes && document.childNodes.length > 0 && document.childNodes[0].data && document.childNodes[0].data.indexOf("DOCTYPE") != -1)) {
            if (d) {
                document.namespaces.add("v", "urn:schemas-microsoft-com:vml")
            }
            this._ie8mode = true
        } else {
            if (d) {
                document.namespaces.add("v", "urn:schemas-microsoft-com:vml");
                document.createStyleSheet().cssText = "v\\:* { behavior: url(#default#VML); display: inline-block; }"
            }
        }
        this.canvas = b[0];
        this._width = Math.max(a.jqx._rup(b.width()), 0);
        this._height = Math.max(a.jqx._rup(b.height()), 0);
        b[0].style.width = this._width + 2;
        b[0].style.height = this._height + 2;
        this._id = new Date().getTime();
        this.clear();
        return true
    }, refresh: function () {
    }, getRect: function () {
        return{x: 0, y: 0, width: this._width, height: this._height}
    }, getContainer: function () {
        var b = this.host.find(".chartContainer");
        return b
    }, clear: function () {
        while (this.canvas.childElementCount > 0) {
            this.canvas.removeChild(this.canvas.firstElementChild)
        }
        this._gradients = {};
        this._defaultParent = undefined
    }, removeElement: function (b) {
        if (b != null) {
            b.parentNode.removeChild(b)
        }
    }, _openGroups: [], beginGroup: function () {
        var b = this._activeParent();
        var c = document.createElement("v:group");
        c.style.position = "absolute";
        c.coordorigin = "0,0";
        c.coordsize = this._width + "," + this._height;
        c.style.left = 0;
        c.style.top = 0;
        c.style.width = this._width;
        c.style.height = this._height;
        b.appendChild(c);
        this._openGroups.push(c);
        return c
    }, endGroup: function () {
        if (this._openGroups.length == 0) {
            return
        }
        this._openGroups.pop()
    }, _activeParent: function () {
        return this._openGroups.length == 0 ? this.canvas : this._openGroups[this._openGroups.length - 1]
    }, createClipRect: function (b) {
        var c = document.createElement("div");
        c.style.height = (b.height + 1) + "px";
        c.style.width = (b.width + 1) + "px";
        c.style.position = "absolute";
        c.style.left = b.x + "px";
        c.style.top = b.y + "px";
        c.style.overflow = "hidden";
        this._clipId = this._clipId || 0;
        c.id = "cl" + this._id + "_" + (++this._clipId).toString();
        this._activeParent().appendChild(c);
        return c
    }, setClip: function (c, b) {
    }, _clipId: 0, addHandler: function (b, d, c) {
        if (a(b).on) {
            a(b).on(d, c)
        } else {
            a(b).bind(d, c)
        }
    }, _getTextParts: function (o, f, g) {
        var e = {width: 0, height: 0, parts: []};
        var m = 0.6;
        var p = o.toString().split("<br>");
        var n = this._activeParent();
        var j = document.createElement("v:textbox");
        this.attr(j, g);
        n.appendChild(j);
        for (var h = 0; h < p.length; h++) {
            var c = p[h];
            var d = document.createElement("span");
            d.appendChild(document.createTextNode(c));
            j.appendChild(d);
            if (g && g["class"]) {
                d.className = g["class"]
            }
            var l = a(j);
            var k = a.jqx._rup(l.width());
            var b = a.jqx._rup(l.height() * m);
            if (b == 0 && a.jqx.browser.msie && parseInt(a.jqx.browser.version) < 9) {
                var q = l.css("font-size");
                if (q) {
                    b = parseInt(q);
                    if (isNaN(b)) {
                        b = 0
                    }
                }
            }
            j.removeChild(d);
            e.width = Math.max(e.width, k);
            e.height += b + (h > 0 ? 2 : 0);
            e.parts.push({width: k, height: b, text: c})
        }
        n.removeChild(j);
        return e
    }, _measureText: function (e, d, c, b) {
        if (Math.abs(d) > 45) {
            d = 90
        } else {
            d = 0
        }
        return a.jqx.commonRenderer.measureText(e, d, c, b, this)
    }, measureText: function (d, c, b) {
        return this._measureText(d, c, b, false)
    }, text: function (r, n, m, A, t, G, I, H, q, g) {
        var B;
        if (I && I.stroke) {
            B = I.stroke
        }
        if (B == undefined) {
            B = "black"
        }
        var s = this._measureText(r, G, I, true);
        var e = s.textPartsInfo;
        var b = e.parts;
        var J = s.width;
        var j = s.height;
        if (isNaN(A) || A == 0) {
            A = J
        }
        if (isNaN(t) || t == 0) {
            t = j
        }
        var v;
        if (!q) {
            q = "center"
        }
        if (!g) {
            g = "center"
        }
        if (b.length > 0 || H) {
            v = this.beginGroup()
        }
        if (H) {
            var c = this.createClipRect({x: a.jqx._rup(n), y: a.jqx._rup(m), width: a.jqx._rup(A), height: a.jqx._rup(t)});
            this.setClip(v, c)
        }
        var l = this._activeParent();
        var p = A || 0;
        var F = t || 0;
        if (Math.abs(G) > 45) {
            G = 90
        } else {
            G = 0
        }
        var u = 0, E = 0;
        if (q == "center") {
            u += (p - J) / 2
        } else {
            if (q == "right") {
                u += (p - J)
            }
        }
        if (g == "center") {
            E = (F - j) / 2
        } else {
            if (g == "bottom") {
                E = F - j
            }
        }
        if (G == 0) {
            m += j + E;
            n += u
        } else {
            n += J + u;
            m += E
        }
        var k = 0, K = 0;
        var d;
        for (var D = b.length - 1; D >= 0; D--) {
            var z = b[D];
            var o = (J - z.width) / 2;
            if (G == 0 && q == "left") {
                o = 0
            } else {
                if (G == 0 && q == "right") {
                    o = J - z.width
                } else {
                    if (G == 90) {
                        o = (j - z.width) / 2
                    }
                }
            }
            var f = k - z.height;
            E = G == 90 ? o : f;
            u = G == 90 ? f : o;
            d = document.createElement("v:textbox");
            d.style.position = "absolute";
            d.style.left = a.jqx._rup(n + u);
            d.style.top = a.jqx._rup(m + E);
            d.style.width = a.jqx._rup(z.width);
            d.style.height = a.jqx._rup(z.height);
            if (G == 90) {
                d.style.filter = "progid:DXImageTransform.Microsoft.BasicImage(rotation=3)"
            }
            var C = document.createElement("span");
            C.appendChild(document.createTextNode(z.text));
            if (I && I["class"]) {
                C.className = I["class"]
            }
            d.appendChild(C);
            l.appendChild(d);
            k -= z.height + (D > 0 ? 2 : 0)
        }
        if (v) {
            this.endGroup();
            return l
        }
        return d
    }, shape: function (b, e) {
        var c = document.createElement(this._createElementMarkup(b));
        if (!c) {
            return undefined
        }
        for (var d in e) {
            c.setAttribute(d, e[d])
        }
        this._activeParent().appendChild(c);
        return c
    }, line: function (e, g, d, f, h) {
        var b = "M " + e + "," + g + " L " + d + "," + f + " X E";
        var c = this.path(b);
        this.attr(c, h);
        return c
    }, _createElementMarkup: function (b) {
        var c = "<v:" + b + ' style=""></v:' + b + ">";
        if (this._ie8mode) {
            c = c.replace('style=""', 'style="behavior: url(#default#VML);"')
        }
        return c
    }, path: function (c, d) {
        var b = document.createElement(this._createElementMarkup("shape"));
        b.style.position = "absolute";
        b.coordsize = this._width + " " + this._height;
        b.coordorigin = "0 0";
        b.style.width = parseInt(this._width);
        b.style.height = parseInt(this._height);
        b.style.left = 0 + "px";
        b.style.top = 0 + "px";
        b.setAttribute("path", c);
        this._activeParent().appendChild(b);
        if (d) {
            this.attr(b, d)
        }
        return b
    }, rect: function (b, g, c, d, f) {
        b = a.jqx._ptrnd(b);
        g = a.jqx._ptrnd(g);
        c = a.jqx._rup(c);
        d = a.jqx._rup(d);
        var e = this.shape("rect", f);
        e.style.position = "absolute";
        e.style.left = b;
        e.style.top = g;
        e.style.width = c;
        e.style.height = d;
        e.strokeweight = 0;
        if (f) {
            this.attr(e, f)
        }
        return e
    }, circle: function (b, f, d, e) {
        var c = this.shape("oval");
        b = a.jqx._ptrnd(b - d);
        f = a.jqx._ptrnd(f - d);
        d = a.jqx._rup(d);
        c.style.position = "absolute";
        c.style.left = b;
        c.style.top = f;
        c.style.width = d * 2;
        c.style.height = d * 2;
        if (e) {
            this.attr(c, e)
        }
        return c
    }, updateCircle: function (d, b, e, c) {
        if (b == undefined) {
            b = parseFloat(d.style.left) + parseFloat(d.style.width) / 2
        }
        if (e == undefined) {
            e = parseFloat(d.style.top) + parseFloat(d.style.height) / 2
        }
        if (c == undefined) {
            c = parseFloat(d.width) / 2
        }
        b = a.jqx._ptrnd(b - c);
        e = a.jqx._ptrnd(e - c);
        c = a.jqx._rup(c);
        d.style.left = b;
        d.style.top = e;
        d.style.width = c * 2;
        d.style.height = c * 2
    }, pieSlicePath: function (k, j, h, r, B, C, d) {
        if (!r) {
            r = 1
        }
        var m = Math.abs(B - C);
        var p = m > 180 ? 1 : 0;
        if (m > 360) {
            B = 0;
            C = 360
        }
        var q = B * Math.PI * 2 / 360;
        var i = C * Math.PI * 2 / 360;
        var w = k, v = k, f = j, e = j;
        var n = !isNaN(h) && h > 0;
        if (n) {
            d = 0
        }
        if (d > 0) {
            var l = m / 2 + B;
            var A = l * Math.PI * 2 / 360;
            k += d * Math.cos(A);
            j -= d * Math.sin(A)
        }
        if (n) {
            var u = h;
            w = a.jqx._ptrnd(k + u * Math.cos(q));
            f = a.jqx._ptrnd(j - u * Math.sin(q));
            v = a.jqx._ptrnd(k + u * Math.cos(i));
            e = a.jqx._ptrnd(j - u * Math.sin(i))
        }
        var t = a.jqx._ptrnd(k + r * Math.cos(q));
        var s = a.jqx._ptrnd(k + r * Math.cos(i));
        var c = a.jqx._ptrnd(j - r * Math.sin(q));
        var b = a.jqx._ptrnd(j - r * Math.sin(i));
        r = a.jqx._ptrnd(r);
        h = a.jqx._ptrnd(h);
        k = a.jqx._ptrnd(k);
        j = a.jqx._ptrnd(j);
        var g = Math.round(B * 65535);
        var z = Math.round((C - B) * 65536);
        if (h < 0) {
            h = 1
        }
        var o = "";
        if (n) {
            o = "M" + w + " " + f;
            o += " AE " + k + " " + j + " " + h + " " + h + " " + g + " " + z;
            o += " L " + s + " " + b;
            g = Math.round((B - C) * 65535);
            z = Math.round(C * 65536);
            o += " AE " + k + " " + j + " " + r + " " + r + " " + z + " " + g;
            o += " L " + w + " " + f
        } else {
            o = "M" + k + " " + j;
            o += " AE " + k + " " + j + " " + r + " " + r + " " + g + " " + z
        }
        o += " X E";
        return o
    }, pieslice: function (k, i, h, e, g, b, j, d) {
        var f = this.pieSlicePath(k, i, h, e, g, b, j);
        var c = this.path(f, d);
        if (d) {
            this.attr(c, d)
        }
        return c
    }, _keymap: [
        {svg: "fill", vml: "fillcolor"},
        {svg: "stroke", vml: "strokecolor"},
        {svg: "stroke-width", vml: "strokeweight"},
        {svg: "stroke-dasharray", vml: "dashstyle"},
        {svg: "fill-opacity", vml: "fillopacity"},
        {svg: "stroke-opacity", vml: "strokeopacity"},
        {svg: "opacity", vml: "opacity"},
        {svg: "cx", vml: "style.left"},
        {svg: "cy", vml: "style.top"},
        {svg: "height", vml: "style.height"},
        {svg: "width", vml: "style.width"},
        {svg: "x", vml: "style.left"},
        {svg: "y", vml: "style.top"},
        {svg: "d", vml: "v"},
        {svg: "display", vml: "style.display"}
    ], _translateParam: function (b) {
        for (var c in this._keymap) {
            if (this._keymap[c].svg == b) {
                return this._keymap[c].vml
            }
        }
        return b
    }, attr: function (c, e) {
        if (!c || !e) {
            return
        }
        for (var d in e) {
            var b = this._translateParam(d);
            if (b == "fillcolor" && e[d].indexOf("grd") != -1) {
                c.type = e[d]
            } else {
                if (b == "opacity" || b == "fillopacity") {
                    if (c.fill) {
                        c.fill.opacity = e[d]
                    }
                } else {
                    if (b == "textContent") {
                        c.children[0].innerText = e[d]
                    } else {
                        if (b == "dashstyle") {
                            c.dashstyle = e[d].replace(",", " ")
                        } else {
                            if (b.indexOf("style.") == -1) {
                                c[b] = e[d]
                            } else {
                                c.style[b.replace("style.", "")] = e[d]
                            }
                        }
                    }
                }
            }
        }
    }, getAttr: function (d, c) {
        var b = this._translateParam(c);
        if (b == "opacity" || b == "fillopacity") {
            if (d.fill) {
                return d.fill.opacity
            } else {
                return 1
            }
        }
        if (b.indexOf("style.") == -1) {
            return d[b]
        }
        return d.style[b.replace("style.", "")]
    }, _gradients: {}, _toRadialGradient: function (b, d, c) {
        return b
    }, _toLinearGradient: function (g, i, j) {
        if (this._ie8mode) {
            return g
        }
        var d = "grd" + g.replace("#", "") + (i ? "v" : "h");
        var e = "#" + d + "";
        if (this._gradients[e]) {
            return e
        }
        var f = document.createElement(this._createElementMarkup("fill"));
        f.type = "gradient";
        f.method = "linear";
        f.angle = i ? 0 : 90;
        var c = "";
        for (var h in j) {
            if (h > 0) {
                c += ", "
            }
            c += j[h][0] + "% " + a.jqx._adjustColor(g, j[h][1])
        }
        f.colors = c;
        var b = document.createElement(this._createElementMarkup("shapetype"));
        b.appendChild(f);
        b.id = d;
        this.canvas.appendChild(b);
        return e
    }};
    a.jqx.HTML5Renderer = function () {
    };
    a.jqx.ptrnd = function (c) {
        if (Math.abs(Math.round(c) - c) == 0.5) {
            return c
        }
        var b = Math.round(c);
        if (b < c) {
            b = b - 1
        }
        return b + 0.5
    };
    a.jqx.HTML5Renderer.prototype = {_elements: {}, init: function (b) {
        try {
            this.host = b;
            this.host.append("<canvas id='__jqxCanvasWrap' style='width:100%; height: 100%;'/>");
            this.canvas = b.find("#__jqxCanvasWrap");
            this.canvas[0].width = b.width();
            this.canvas[0].height = b.height();
            this.ctx = this.canvas[0].getContext("2d")
        } catch (c) {
            return false
        }
        return true
    }, getContainer: function () {
        if (this.canvas && this.canvas.length == 1) {
            return this.canvas
        }
        return undefined
    }, getRect: function () {
        return{x: 0, y: 0, width: this.canvas[0].width - 1, height: this.canvas[0].height - 1}
    }, beginGroup: function () {
    }, endGroup: function () {
    }, setClip: function () {
    }, createClipRect: function (b) {
    }, addHandler: function (b, d, c) {
    }, clear: function () {
        this._elements = {};
        this._maxId = 0;
        this._renderers._gradients = {};
        this._gradientId = 0
    }, removeElement: function (b) {
        if (undefined == b) {
            return
        }
        if (this._elements[b.id]) {
            delete this._elements[b.id]
        }
    }, _maxId: 0, shape: function (b, e) {
        var c = {type: b, id: this._maxId++};
        for (var d in e) {
            c[d] = e[d]
        }
        this._elements[c.id] = c;
        return c
    }, attr: function (b, d) {
        for (var c in d) {
            b[c] = d[c]
        }
    }, rect: function (b, g, c, e, f) {
        if (isNaN(b)) {
            throw'Invalid value for "x"'
        }
        if (isNaN(g)) {
            throw'Invalid value for "y"'
        }
        if (isNaN(c)) {
            throw'Invalid value for "width"'
        }
        if (isNaN(e)) {
            throw'Invalid value for "height"'
        }
        var d = this.shape("rect", {x: b, y: g, width: c, height: e});
        if (f) {
            this.attr(d, f)
        }
        return d
    }, path: function (b, d) {
        var c = this.shape("path", d);
        this.attr(c, {d: b});
        return c
    }, line: function (c, e, b, d, f) {
        return this.path("M " + c + "," + e + " L " + b + "," + d, f)
    }, circle: function (b, f, d, e) {
        var c = this.shape("circle", {x: b, y: f, r: d});
        if (e) {
            this.attr(c, e)
        }
        return c
    }, pieSlicePath: function (c, h, g, e, f, d, b) {
        return a.jqx.commonRenderer.pieSlicePath(c, h, g, e, f, d, b)
    }, pieslice: function (j, h, g, e, f, b, i, c) {
        var d = this.path(this.pieSlicePath(j, h, g, e, f, b, i), c);
        this.attr(d, {x: j, y: h, innerRadius: g, outerRadius: e, angleFrom: f, angleTo: b});
        return d
    }, _getCSSStyle: function (c) {
        var g = document.styleSheets;
        try {
            for (var d = 0; d < g.length; d++) {
                for (var b = 0; g[d].cssRules && b < g[d].cssRules.length; b++) {
                    if (g[d].cssRules[b].selectorText.indexOf(c) != -1) {
                        return g[d].cssRules[b].style
                    }
                }
            }
        } catch (f) {
        }
        return{}
    }, _getTextParts: function (p, f, g) {
        var l = "Arial";
        var q = "10pt";
        var m = "";
        if (g && g["class"]) {
            var b = this._getCSSStyle(g["class"]);
            if (b.fontSize) {
                q = b.fontSize
            }
            if (b.fontFamily) {
                l = b.fontFamily
            }
            if (b.fontWeight) {
                m = b.fontWeight
            }
        }
        this.ctx.font = m + " " + q + " " + l;
        var e = {width: 0, height: 0, parts: []};
        var k = 0.6;
        var o = p.toString().split("<br>");
        for (var h = 0; h < o.length; h++) {
            var d = o[h];
            var j = this.ctx.measureText(d).width;
            var n = document.createElement("span");
            n.font = this.ctx.font;
            n.textContent = d;
            document.body.appendChild(n);
            var c = n.offsetHeight * k;
            document.body.removeChild(n);
            e.width = Math.max(e.width, a.jqx._rup(j));
            e.height += c + (h > 0 ? 4 : 0);
            e.parts.push({width: j, height: c, text: d})
        }
        return e
    }, _measureText: function (e, d, c, b) {
        return a.jqx.commonRenderer.measureText(e, d, c, b, this)
    }, measureText: function (d, c, b) {
        return this._measureText(d, c, b, false)
    }, text: function (m, l, j, c, n, f, g, d, h, k, e) {
        var o = this.shape("text", {text: m, x: l, y: j, width: c, height: n, angle: f, clip: d, halign: h, valign: k, rotateAround: e});
        if (g) {
            this.attr(o, g)
        }
        o.fontFamily = "Arial";
        o.fontSize = "10pt";
        o.fontWeight = "";
        o.color = "#000000";
        if (g && g["class"]) {
            var b = this._getCSSStyle(g["class"]);
            o.fontFamily = b.fontFamily || o.fontFamily;
            o.fontSize = b.fontSize || o.fontSize;
            o.fontWeight = b.fontWeight || o.fontWeight;
            o.color = b.color || o.color
        }
        var i = this._measureText(m, 0, g, true);
        this.attr(o, {textPartsInfo: i.textPartsInfo, textWidth: i.width, textHeight: i.height});
        if (c <= 0 || isNaN(c)) {
            this.attr(o, {width: i.width})
        }
        if (n <= 0 || isNaN(n)) {
            this.attr(o, {height: i.height})
        }
        return o
    }, _toLinearGradient: function (c, g, f) {
        if (this._renderers._gradients[c]) {
            return c
        }
        var b = [];
        for (var e = 0; e < f.length; e++) {
            b.push({percent: f[e][0] / 100, color: a.jqx._adjustColor(c, f[e][1])})
        }
        var d = "gr" + this._gradientId++;
        this.createGradient(d, g ? "vertical" : "horizontal", b);
        return d
    }, _toRadialGradient: function (c, f) {
        if (this._renderers._gradients[c]) {
            return c
        }
        var b = [];
        for (var e = 0; e < f.length; e++) {
            b.push({percent: f[e][0] / 100, color: a.jqx._adjustColor(c, f[e][1])})
        }
        var d = "gr" + this._gradientId++;
        this.createGradient(d, "radial", b);
        return d
    }, _gradientId: 0, createGradient: function (d, c, b) {
        this._renderers.createGradient(d, c, b)
    }, _renderers: {_gradients: {}, createGradient: function (d, c, b) {
        this._gradients[d] = {orientation: c, colorStops: b}
    }, setStroke: function (b, c) {
        b.strokeStyle = c.stroke || "transparent";
        b.lineWidth = c["stroke-width"] || 1;
        if (c["fill-opacity"]) {
            b.globalAlpha = c["fill-opacity"]
        } else {
            b.globalAlpha = 1
        }
        if (b.setLineDash) {
            if (c["stroke-dasharray"]) {
                b.setLineDash(c["stroke-dasharray"].split(","))
            } else {
                b.setLineDash([])
            }
        }
    }, setFillStyle: function (m, e) {
        m.fillStyle = "transparent";
        if (e["fill-opacity"]) {
            m.globalAlpha = e["fill-opacity"]
        } else {
            m.globalAlpha = 1
        }
        if (e.fill && e.fill.indexOf("#") == -1 && this._gradients[e.fill]) {
            var k = this._gradients[e.fill].orientation != "horizontal";
            var g = this._gradients[e.fill].orientation == "radial";
            var c = a.jqx.ptrnd(e.x);
            var l = a.jqx.ptrnd(e.y);
            var b = a.jqx.ptrnd(e.x + (k ? 0 : e.width));
            var h = a.jqx.ptrnd(e.y + (k ? e.height : 0));
            var j;
            if ((e.type == "circle" || e.type == "path") && g) {
                x = a.jqx.ptrnd(e.x);
                y = a.jqx.ptrnd(e.y);
                r1 = e.innerRadius || 0;
                r2 = e.outerRadius || e.r || 0;
                j = m.createRadialGradient(x, y, r1, x, y, r2)
            }
            if (!g) {
                if (isNaN(c) || isNaN(b) || isNaN(l) || isNaN(h)) {
                    c = 0;
                    l = 0;
                    b = k ? 0 : m.canvas.width;
                    h = k ? m.canvas.height : 0
                }
                j = m.createLinearGradient(c, l, b, h)
            }
            var d = this._gradients[e.fill].colorStops;
            for (var f = 0; f < d.length; f++) {
                j.addColorStop(d[f].percent, d[f].color)
            }
            m.fillStyle = j
        } else {
            if (e.fill) {
                m.fillStyle = e.fill
            }
        }
    }, rect: function (b, c) {
        if (c.width == 0 || c.height == 0) {
            return
        }
        b.fillRect(a.jqx.ptrnd(c.x), a.jqx.ptrnd(c.y), c.width, c.height);
        b.strokeRect(a.jqx.ptrnd(c.x), a.jqx.ptrnd(c.y), c.width, c.height)
    }, circle: function (b, c) {
        if (c.r == 0) {
            return
        }
        b.beginPath();
        b.arc(a.jqx.ptrnd(c.x), a.jqx.ptrnd(c.y), c.r, 0, Math.PI * 2, false);
        b.closePath();
        b.fill();
        b.stroke()
    }, _parsePoint: function (c) {
        var b = this._parseNumber(c);
        var d = this._parseNumber(c);
        return({x: b, y: d})
    }, _parseNumber: function (d) {
        var e = false;
        for (var b = this._pos; b < d.length; b++) {
            if ((d[b] >= "0" && d[b] <= "9") || d[b] == "." || (d[b] == "-" && !e)) {
                e = true;
                continue
            }
            if (!e && (d[b] == " " || d[b] == ",")) {
                this._pos++;
                continue
            }
            break
        }
        var c = parseFloat(d.substring(this._pos, b));
        if (isNaN(c)) {
            return undefined
        }
        this._pos = b;
        return c
    }, _pos: 0, _cmds: "mlcaz", _lastCmd: "", _isRelativeCmd: function (b) {
        return a.jqx.string.contains(this._cmds, b)
    }, _parseCmd: function (b) {
        for (var c = this._pos; c < b.length; c++) {
            if (a.jqx.string.containsIgnoreCase(this._cmds, b[c])) {
                this._pos = c + 1;
                this._lastCmd = b[c];
                return this._lastCmd
            }
            if (b[c] == " ") {
                this._pos++;
                continue
            }
            if (b[c] >= "0" && b[c] <= "9") {
                this._pos = c;
                if (this._lastCmd == "") {
                    break
                } else {
                    return this._lastCmd
                }
            }
        }
        return undefined
    }, _toAbsolutePoint: function (b) {
        return{x: this._currentPoint.x + b.x, y: this._currentPoint.y + b.y}
    }, _currentPoint: {x: 0, y: 0}, path: function (C, L) {
        var z = L.d;
        this._pos = 0;
        this._lastCmd = "";
        var k = undefined;
        this._currentPoint = {x: 0, y: 0};
        C.beginPath();
        var G = 0;
        while (this._pos < z.length) {
            var F = this._parseCmd(z);
            if (F == undefined) {
                break
            }
            if (F == "M" || F == "m") {
                var D = this._parsePoint(z);
                if (D == undefined) {
                    break
                }
                C.moveTo(D.x, D.y);
                this._currentPoint = D;
                if (k == undefined) {
                    k = D
                }
                continue
            }
            if (F == "L" || F == "l") {
                var D = this._parsePoint(z);
                if (D == undefined) {
                    break
                }
                C.lineTo(D.x, D.y);
                this._currentPoint = D;
                continue
            }
            if (F == "A" || F == "a") {
                var g = this._parseNumber(z);
                var f = this._parseNumber(z);
                var J = this._parseNumber(z) * (Math.PI / 180);
                var N = this._parseNumber(z);
                var e = this._parseNumber(z);
                var o = this._parsePoint(z);
                if (this._isRelativeCmd(F)) {
                    o = this._toAbsolutePoint(o)
                }
                if (g == 0 || f == 0) {
                    continue
                }
                var h = this._currentPoint;
                var I = {x: Math.cos(J) * (h.x - o.x) / 2 + Math.sin(J) * (h.y - o.y) / 2, y: -Math.sin(J) * (h.x - o.x) / 2 + Math.cos(J) * (h.y - o.y) / 2};
                var j = Math.pow(I.x, 2) / Math.pow(g, 2) + Math.pow(I.y, 2) / Math.pow(f, 2);
                if (j > 1) {
                    g *= Math.sqrt(j);
                    f *= Math.sqrt(j)
                }
                var p = (N == e ? -1 : 1) * Math.sqrt(((Math.pow(g, 2) * Math.pow(f, 2)) - (Math.pow(g, 2) * Math.pow(I.y, 2)) - (Math.pow(f, 2) * Math.pow(I.x, 2))) / (Math.pow(g, 2) * Math.pow(I.y, 2) + Math.pow(f, 2) * Math.pow(I.x, 2)));
                if (isNaN(p)) {
                    p = 0
                }
                var H = {x: p * g * I.y / f, y: p * -f * I.x / g};
                var B = {x: (h.x + o.x) / 2 + Math.cos(J) * H.x - Math.sin(J) * H.y, y: (h.y + o.y) / 2 + Math.sin(J) * H.x + Math.cos(J) * H.y};
                var A = function (i) {
                    return Math.sqrt(Math.pow(i[0], 2) + Math.pow(i[1], 2))
                };
                var t = function (m, i) {
                    return(m[0] * i[0] + m[1] * i[1]) / (A(m) * A(i))
                };
                var M = function (m, i) {
                    return(m[0] * i[1] < m[1] * i[0] ? -1 : 1) * Math.acos(t(m, i))
                };
                var E = M([1, 0], [(I.x - H.x) / g, (I.y - H.y) / f]);
                var n = [(I.x - H.x) / g, (I.y - H.y) / f];
                var l = [(-I.x - H.x) / g, (-I.y - H.y) / f];
                var K = M(n, l);
                if (t(n, l) <= -1) {
                    K = Math.PI
                }
                if (t(n, l) >= 1) {
                    K = 0
                }
                if (e == 0 && K > 0) {
                    K = K - 2 * Math.PI
                }
                if (e == 1 && K < 0) {
                    K = K + 2 * Math.PI
                }
                var t = (g > f) ? g : f;
                var w = (g > f) ? 1 : g / f;
                var q = (g > f) ? f / g : 1;
                C.translate(B.x, B.y);
                C.rotate(J);
                C.scale(w, q);
                C.arc(0, 0, t, E, E + K, 1 - e);
                C.scale(1 / w, 1 / q);
                C.rotate(-J);
                C.translate(-B.x, -B.y);
                continue
            }
            if ((F == "Z" || F == "z") && k != undefined) {
                C.lineTo(k.x, k.y);
                this._currentPoint = k;
                continue
            }
            if (F == "C" || F == "c") {
                var d = this._parsePoint(z);
                var c = this._parsePoint(z);
                var b = this._parsePoint(z);
                C.bezierCurveTo(d.x, d.y, c.x, c.y, b.x, b.y);
                this._currentPoint = b;
                continue
            }
        }
        C.fill();
        C.stroke();
        C.closePath()
    }, text: function (u, D) {
        var n = a.jqx.ptrnd(D.x);
        var m = a.jqx.ptrnd(D.y);
        var s = a.jqx.ptrnd(D.width);
        var q = a.jqx.ptrnd(D.height);
        var p = D.halign;
        var g = D.valign;
        var A = D.angle;
        var b = D.rotateAround;
        var e = D.textPartsInfo;
        var d = e.parts;
        var B = D.clip;
        if (B == undefined) {
            B = true
        }
        u.save();
        if (!p) {
            p = "center"
        }
        if (!g) {
            g = "center"
        }
        if (B) {
            u.rect(n, m, s, q);
            u.clip()
        }
        var E = D.textWidth;
        var j = D.textHeight;
        var o = s || 0;
        var z = q || 0;
        u.fillStyle = D.color;
        u.font = D.fontWeight + " " + D.fontSize + " " + D.fontFamily;
        if (!A || A == 0) {
            m += j;
            if (g == "center") {
                m += (z - j) / 2
            } else {
                if (g == "bottom") {
                    m += z - j
                }
            }
            if (!s) {
                s = E
            }
            if (!q) {
                q = j
            }
            var l = 0;
            for (var v = d.length - 1; v >= 0; v--) {
                var r = d[v];
                var F = n;
                var k = d[v].width;
                var c = d[v].height;
                if (p == "center") {
                    F += (o - k) / 2
                } else {
                    if (p == "right") {
                        F += (o - k)
                    }
                }
                u.fillText(r.text, F, m + l);
                l -= r.height + (v > 0 ? 4 : 0)
            }
            u.restore();
            return
        }
        var t = a.jqx.commonRenderer.alignTextInRect(n, m, s, q, E, j, p, g, A, b);
        n = t.x;
        m = t.y;
        var f = A * Math.PI * 2 / 360;
        u.translate(n, m);
        u.rotate(f);
        var l = 0;
        var C = e.width;
        for (var v = d.length - 1; v >= 0; v--) {
            var F = 0;
            if (p == "center") {
                F += (C - d[v].width) / 2
            } else {
                if (p == "right") {
                    F += (C - d[v].width)
                }
            }
            u.fillText(d[v].text, F, l);
            l -= d[v].height + 4
        }
        u.restore()
    }}, refresh: function () {
        this.ctx.clearRect(0, 0, this.canvas[0].width, this.canvas[0].height);
        for (var b in this._elements) {
            var c = this._elements[b];
            this._renderers.setFillStyle(this.ctx, c);
            this._renderers.setStroke(this.ctx, c);
            this._renderers[this._elements[b].type](this.ctx, c)
        }
    }}
})(jQuery);