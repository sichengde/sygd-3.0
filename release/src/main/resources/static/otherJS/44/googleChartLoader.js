(function () {
    var aa = "\n//# sourceURL=", ba = '<script type="text/javascript" src="', b = "American Samoa", ca = "Antigua and Barbuda", k = "Bolivia", p = "Bosna i Hercegovina", q = "Botswana", da = "British Virgin Islands", ea = "Cayman Islands", fa = "Christmas Island", ga = "Falkland Islands", r = "Ghana", ha = "Guin\u00e9e \u00e9quatoriale", ia = "Guyane fran\u00e7aise", t = "Honduras", u = "Indonesia", v = "Itoophiyaa", ja = "Kalaallit Nunaat", ka = "Kiribati", la = "LocaleNameConstants", ma = "Luxembourg", na = "Madagascar", oa = "Marshall Islands", x = "Micronesia", pa = "Moldova, Republica",
        qa = "Nederlandse Antillen", ra = "New Zealand", y = "Nigeria", sa = "Norfolk Island", ta = "Northern Mariana Islands", ua = "Nouvelle-Cal\u00e9donie", z = "Papua New Guinea", va = "Paraguay", wa = "Philippines", xa = "Polyn\u00e9sie fran\u00e7aise", ya = "Puerto Rico", za = "Rep\u00fablica Dominicana", A = "Rwanda", Aa = "Rywvaneth Unys", Ba = "R\u00e9publique centrafricaine", Ca = "R\u00e9publique d\u00e9mocratique du Congo", Da = "Saint Kitts and Nevis", Ea = "Saint Vincent and the Grenadines", Fa = "Saint-Pierre-et-Miquelon", Ga = "Serbia and Montenegro",
        Ha = "Seychelles", Ia = "Slovensk\u00e1 republika", Ja = "Solomon Islands", B = "South Africa", Ka = "Svalbard og Jan Mayen", La = "Swaziland", Ma = "S\u00e3o Tom\u00e9 e Pr\u00edncipe", C = "S\u00e9n\u00e9gal", Na = "Tanzania", Oa = "Timor Leste", D = "Tokelau", Pa = "Turks and Caicos Islands", E = "Tuvalu", F = "T\u00fcrkiye", Qa = "U.S. Virgin Islands", Ra = "United Kingdom", Sa = "United States", Ta = "United States Minor Outlying Islands", Ua = "Unknown or Invalid Region", G = "Vanuatu", Va = "Wallis-et-Futuna", Wa = "annotatedtimeline", H = "array", I = "browserchart",
        J = "complete", K = "corechart", L = "dygraph", M = "function", N = "google.charts.load", Xa = "google.charts.setOnLoadCallback", O = "imagechart", P = "object", Ya = "text/javascript", Q = "ui", R = "ui_base", S = "webfontloader", Za = "{css_prefix}/{cssFile}", $a = "{prefix}", ab = "{prefix}/{version}/third_party/{package}", bb = "{version}", cb = "\u010cesk\u00e1 republika", db = "\u0411\u0435\u043b\u0430\u0440\u0443\u0441\u044c", eb = "\u041a\u044b\u0440\u0433\u044b\u0437\u0441\u0442\u0430\u043d", fb = "\u043c\u043e\u043d\u0433\u043e\u043b\u044c\u0441\u043a\u0438\u0439",
        gb = "\u0540\u0561\u0575\u0561\u057d\u057f\u0561\u0576\u056b \u0540\u0561\u0576\u0580\u0561\u057a\u0565\u057f\u0578\u0582\u0569\u056b\u0582\u0576", T = "\u0627\u0641\u063a\u0627\u0646\u0633\u062a\u0627\u0646", hb = "\u0627\u0644\u0627\u0645\u0627\u0631\u0627\u062a \u0627\u0644\u0639\u0631\u0628\u064a\u0629 \u0627\u0644\u0645\u062a\u062d\u062f\u0629", ib = "\u0627\u0644\u0635\u062d\u0631\u0627\u0621 \u0627\u0644\u063a\u0631\u0628\u064a\u0629", jb = "\u0627\u0644\u0645\u0645\u0644\u0643\u0629 \u0627\u0644\u0639\u0631\u0628\u064a\u0629 \u0627\u0644\u0633\u0639\u0648\u062f\u064a\u0629",
        kb = "\u0627\u0644\u0648\u0644\u0627\u064a\u0627\u062a \u0627\u0644\u0645\u062a\u062d\u062f\u0629 \u0627\u0644\u0623\u0645\u0631\u064a\u0643\u064a\u0629", lb = "\u062c\u0632\u0631 \u0627\u0644\u0642\u0645\u0631", V = "\u067e\u0627\u06a9\u0633\u062a\u0627\u0646", W = "\u092d\u093e\u0930\u0924", X = "\u12a2\u1275\u12ee\u1335\u12eb", mb = "\uc870\uc120 \ubbfc\uc8fc\uc8fc\uc758 \uc778\ubbfc \uacf5\ud654\uad6d";

    function Y() {
        return function () {
        }
    }

    var Z = Z || {};
    Z.global = this;
    Z.S = function (a) {
        return void 0 !== a
    };
    Z.Aa = function (a, c, d) {
        a = a.split(".");
        d = d || Z.global;
        a[0] in d || !d.execScript || d.execScript("var " + a[0]);
        for (var e; a.length && (e = a.shift());)!a.length && Z.S(c) ? d[e] = c : d = d[e] ? d[e] : d[e] = {}
    };
    Z.Ee = function (a, c) {
        Z.Aa(a, c)
    };
    Z.H = !0;
    Z.Kd = "en";
    Z.ra = !0;
    Z.ic = !1;
    Z.Sb = !Z.H;
    Z.$a = !1;
    Z.Nf = function (a) {
        if (Z.Ma())throw Error("goog.provide can not be used within a goog.module.");
        Z.ib(a)
    };
    Z.ib = function (a, c) {
        Z.Aa(a, c)
    };
    Z.tc = /^[a-zA-Z_$][a-zA-Z0-9._$]*$/;
    Z.Qa = function (a) {
        if (!Z.h(a) || !a || -1 == a.search(Z.tc))throw Error("Invalid module identifier");
        if (!Z.Ma())throw Error("Module " + a + " has been loaded incorrectly.");
        if (Z.l.Ra)throw Error("goog.module may only be called once per module.");
        Z.l.Ra = a
    };
    Z.Qa.get = Y();
    Z.Qa.Pe = Y();
    Z.l = null;
    Z.Ma = function () {
        return null != Z.l
    };
    Z.Qa.xa = function () {
        Z.l.xa = !0
    };
    Z.gg = function (a) {
        if (Z.Sb)throw a = a || "", Error("Importing test-only code into non-debug environment" + (a ? ": " + a : "."));
    };
    Z.Ke = Y();
    Z.Ga = function (a) {
        a = a.split(".");
        for (var c = Z.global, d; d = a.shift();)if (Z.Nc(c[d]))c = c[d]; else return null;
        return c
    };
    Z.$e = function (a, c) {
        c = c || Z.global;
        for (var d in a)c[d] = a[d]
    };
    Z.Qd = function (a, c, d, e) {
        if (Z.Ya) {
            var f;
            a = a.replace(/\\/g, "/");
            var g = Z.j;
            e && "boolean" !== typeof e || (e = e ? {module: "goog"} : {});
            for (var h = 0; f = c[h]; h++)g.V[f] = a, g.Na[a] = e;
            for (e = 0; c = d[e]; e++)a in g.G || (g.G[a] = {}), g.G[a][c] = !0
        }
    };
    Z.Gg = !1;
    Z.Hd = !0;
    Z.$c = function (a) {
        Z.global.console && Z.global.console.error(a)
    };
    Z.ag = Y();
    Z.B = "";
    Z.If = Y();
    Z.Pd = function () {
        throw Error("unimplemented abstract method");
    };
    Z.Rd = function (a) {
        a.Oe = function () {
            if (a.xb)return a.xb;
            Z.H && (Z.yb[Z.yb.length] = a);
            return a.xb = new a
        }
    };
    Z.yb = [];
    Z.Zb = !0;
    Z.gc = Z.H;
    Z.Zc = {};
    Z.Ya = !1;
    Z.cb = "detect";
    Z.pc = "transpile.js";
    Z.Ya && (Z.j = {Na: {}, V: {}, G: {}, Lb: {}, Xa: {}, Y: {}}, Z.vb = function () {
        var a = Z.global.document;
        return null != a && "write" in a
    }, Z.Fc = function () {
        if (Z.S(Z.global.Pb))Z.B = Z.global.Pb; else if (Z.vb())for (var a = Z.global.document.getElementsByTagName("SCRIPT"), c = a.length - 1; 0 <= c; --c) {
            var d = a[c].src, e = d.lastIndexOf("?"), e = -1 == e ? d.length : e;
            if ("base.js" == d.substr(e - 7, 7)) {
                Z.B = d.substr(0, e - 7);
                break
            }
        }
    }, Z.Ia = function (a, c) {
        (Z.global.Ad || Z.xd)(a, c) && (Z.j.Xa[a] = !0)
    }, Z.Yb = !(Z.global.atob || !Z.global.document || !Z.global.document.all),
        Z.Lc = function (a, c, d) {
            Z.Ia("", 'goog.retrieveAndExec_("' + a + '", ' + c + ", " + d + ");")
        }, Z.Ta = [], Z.Ig = function (a, c) {
        return Z.Zb && Z.S(Z.global.JSON) ? "goog.loadModule(" + Z.global.JSON.stringify(c + aa + a + "\n") + ");" : 'goog.loadModule(function(exports) {"use strict";' + c + "\n;return exports});\n//# sourceURL=" + a + "\n"
    }, Z.Yc = function () {
        var a = Z.Ta.length;
        if (0 < a) {
            var c = Z.Ta;
            Z.Ta = [];
            for (var d = 0; d < a; d++)Z.Db(c[d])
        }
    }, Z.Cf = function (a) {
        Z.zb(a) && Z.uc(a) && Z.Db(Z.B + Z.Ha(a))
    }, Z.zb = function (a) {
        var c = (a = Z.Ha(a)) && Z.j.Na[a] || {};
        return a &&
        ("goog" == c.module || Z.Eb(c.lang)) ? Z.B + a in Z.j.Y : !1
    }, Z.uc = function (a) {
        if ((a = Z.Ha(a)) && a in Z.j.G)for (var c in Z.j.G[a])if (!Z.Rc(c) && !Z.zb(c))return !1;
        return !0
    }, Z.Db = function (a) {
        if (a in Z.j.Y) {
            var c = Z.j.Y[a];
            delete Z.j.Y[a];
            Z.Kc(c)
        }
    }, Z.Bf = Y(), Z.wd = function (a) {
        Z.global.document.write(ba + a + '">\x3c/script>')
    }, Z.vc = function (a) {
        var c = Z.global.document, d = c.createElement("script");
        d.type = Ya;
        d.src = a;
        d.defer = !1;
        d.async = !1;
        c.head.appendChild(d)
    }, Z.xd = function (a, c) {
        if (Z.vb()) {
            var d = Z.global.document;
            if (!Z.$a &&
                d.readyState == J) {
                if (/\bdeps.js$/.test(a))return !1;
                throw Error('Cannot write "' + a + '" after document load');
            }
            void 0 === c ? Z.Yb ? (c = " onreadystatechange='goog.onScriptLoad_(this, " + ++Z.Bb + ")' ", d.write(ba + a + '"' + c + ">\x3c/script>")) : Z.$a ? Z.vc(a) : Z.wd(a) : d.write('<script type="text/javascript">' + c + "\x3c/script>");
            return !0
        }
        return !1
    }, Z.Eb = function (a) {
        if ("always" == Z.cb)return !0;
        if ("never" == Z.cb)return !1;
        if (!Z.O) {
            Z.O = {es5: !0, es6: !0, "es6-impl": !0};
            try {
                Z.O.es5 = eval("[1,].length!=1"), eval('(()=>{"use strict";let a={};const X=class{constructor(){}x(z){return new Map([...arguments]).get(z[0])==3}};return new X().x([a,3])})()') &&
                (Z.O["es6-impl"] = !1), eval('(()=>{"use strict";class X{constructor(){if(new.target!=String)throw 1;this.x=42}}let q=Reflect.construct(X,[],String);if(q.x!=42||!(q instanceof String))throw 1;for(const a of[2,3]){if(a==2)continue;function f(z={a}){let a=0;return z.a}{function f(){return 0;}}return f()==3}})()') && (Z.O.es6 = !1)
            } catch (c) {
            }
        }
        return !!Z.O[a]
    }, Z.O = null, Z.Bb = 0, Z.Kf = function (a, c) {
        a.readyState == J && Z.Bb == c && Z.Yc();
        return !0
    }, Z.Jg = function (a) {
        function c(a) {
            if (!(a in f.Xa || a in f.Lb)) {
                f.Lb[a] = !0;
                if (a in
                    f.G)for (var g in f.G[a])if (!Z.Rc(g))if (g in f.V)c(f.V[g]); else throw Error("Undefined nameToPath for " + g);
                a in e || (e[a] = !0, d.push(a))
            }
        }

        var d = [], e = {}, f = Z.j;
        c(a);
        for (a = 0; a < d.length; a++) {
            var g = d[a];
            Z.j.Xa[g] = !0
        }
        var h = Z.l;
        Z.l = null;
        for (a = 0; a < d.length; a++)if (g = d[a]) {
            var l = f.Na[g] || {}, m = Z.Eb(l.lang);
            "goog" == l.module || m ? Z.Lc(Z.B + g, "goog" == l.module, m) : Z.Ia(Z.B + g)
        } else throw Z.l = h, Error("Undefined script input");
        Z.l = h
    }, Z.Ha = function (a) {
        return a in Z.j.V ? Z.j.V[a] : null
    }, Z.Fc(), Z.global.Bd || Z.Ia(Z.B + "deps.js"));
    Z.Af = function (a) {
        var c = Z.l;
        try {
            Z.l = {Ra: void 0, xa: !1};
            var d;
            if (Z.Ab(a))d = a.call(void 0, {}); else if (Z.h(a))d = Z.Wc.call(void 0, a); else throw Error("Invalid module definition");
            var e = Z.l.Ra;
            if (!Z.h(e) || !e)throw Error('Invalid module name "' + e + '"');
            Z.l.xa ? Z.ib(e, d) : Z.gc && Object.seal && Object.seal(d);
            Z.Zc[e] = d
        } finally {
            Z.l = c
        }
    };
    Z.Wc = function (a) {
        eval(a);
        return {}
    };
    Z.Ff = function (a) {
        a = a.split("/");
        for (var c = 0; c < a.length;)"." == a[c] ? a.splice(c, 1) : c && ".." == a[c] && a[c - 1] && ".." != a[c - 1] ? a.splice(--c, 2) : c++;
        return a.join("/")
    };
    Z.Vc = function (a) {
        if (Z.global.Qb)return Z.global.Qb(a);
        try {
            var c = new Z.global.XMLHttpRequest;
            c.open("get", a, !1);
            c.send();
            return 0 == c.status || 200 == c.status ? c.responseText : null
        } catch (d) {
            return null
        }
    };
    Z.cg = Y();
    Z.Bg = function (a, c) {
        var d = Z.global.$jscomp;
        d || (Z.global.$jscomp = d = {});
        var e = d.Ib;
        if (!e) {
            var f = Z.B + Z.pc, g = Z.Vc(f);
            g && (eval(g + aa + f), d = Z.global.$jscomp, e = d.Ib)
        }
        if (!e)var h = " requires transpilation but no transpiler was found.", h = h + ' Please add "//javascript/closure:transpiler" as a data dependency to ensure it is included.', e = d.Ib = function (a, c) {
            Z.$c(c + h);
            return a
        };
        return e(a, c)
    };
    Z.u = function (a) {
        var c = typeof a;
        if (c == P)if (a) {
            if (a instanceof Array)return H;
            if (a instanceof Object)return c;
            var d = Object.prototype.toString.call(a);
            if ("[object Window]" == d)return P;
            if ("[object Array]" == d || "number" == typeof a.length && "undefined" != typeof a.splice && "undefined" != typeof a.propertyIsEnumerable && !a.propertyIsEnumerable("splice"))return H;
            if ("[object Function]" == d || "undefined" != typeof a.call && "undefined" != typeof a.propertyIsEnumerable && !a.propertyIsEnumerable("call"))return M
        } else return "null";
        else if (c == M && "undefined" == typeof a.call)return P;
        return c
    };
    Z.rf = function (a) {
        return null === a
    };
    Z.Nc = function (a) {
        return null != a
    };
    Z.isArray = function (a) {
        return Z.u(a) == H
    };
    Z.Ka = function (a) {
        var c = Z.u(a);
        return c == H || c == P && "number" == typeof a.length
    };
    Z.mf = function (a) {
        return Z.ea(a) && typeof a.getFullYear == M
    };
    Z.h = function (a) {
        return "string" == typeof a
    };
    Z.Mc = function (a) {
        return "boolean" == typeof a
    };
    Z.Qc = function (a) {
        return "number" == typeof a
    };
    Z.Ab = function (a) {
        return Z.u(a) == M
    };
    Z.ea = function (a) {
        var c = typeof a;
        return c == P && null != a || c == M
    };
    Z.sb = function (a) {
        return a[Z.F] || (a[Z.F] = ++Z.rd)
    };
    Z.bf = function (a) {
        return !!a[Z.F]
    };
    Z.hd = function (a) {
        null !== a && "removeAttribute" in a && a.removeAttribute(Z.F);
        try {
            delete a[Z.F]
        } catch (c) {
        }
    };
    Z.F = "closure_uid_" + (1E9 * Math.random() >>> 0);
    Z.rd = 0;
    Z.Me = Z.sb;
    Z.Yf = Z.hd;
    Z.zc = function (a) {
        var c = Z.u(a);
        if (c == P || c == H) {
            if (a.clone)return a.clone();
            var c = c == H ? [] : {}, d;
            for (d in a)c[d] = Z.zc(a[d]);
            return c
        }
        return a
    };
    Z.yc = function (a, c, d) {
        return a.call.apply(a.bind, arguments)
    };
    Z.xc = function (a, c, d) {
        if (!a)throw Error();
        if (2 < arguments.length) {
            var e = Array.prototype.slice.call(arguments, 2);
            return function () {
                var d = Array.prototype.slice.call(arguments);
                Array.prototype.unshift.apply(d, e);
                return a.apply(c, d)
            }
        }
        return function () {
            return a.apply(c, arguments)
        }
    };
    Z.bind = function (a, c, d) {
        Function.prototype.bind && -1 != Function.prototype.bind.toString().indexOf("native code") ? Z.bind = Z.yc : Z.bind = Z.xc;
        return Z.bind.apply(null, arguments)
    };
    Z.dd = function (a, c) {
        var d = Array.prototype.slice.call(arguments, 1);
        return function () {
            var c = d.slice();
            c.push.apply(c, arguments);
            return a.apply(this, c)
        }
    };
    Z.Df = function (a, c) {
        for (var d in c)a[d] = c[d]
    };
    Z.now = Z.ra && Date.now || function () {
            return +new Date
        };
    Z.Kc = function (a) {
        if (Z.global.execScript)Z.global.execScript(a, "JavaScript"); else if (Z.global.eval) {
            if (null == Z.Z)if (Z.global.eval("var _evalTest_ = 1;"), "undefined" != typeof Z.global._evalTest_) {
                try {
                    delete Z.global._evalTest_
                } catch (e) {
                }
                Z.Z = !0
            } else Z.Z = !1;
            if (Z.Z)Z.global.eval(a); else {
                var c = Z.global.document, d = c.createElement("SCRIPT");
                d.type = Ya;
                d.defer = !1;
                d.appendChild(c.createTextNode(a));
                c.body.appendChild(d);
                c.body.removeChild(d)
            }
        } else throw Error("goog.globalEval not available");
    };
    Z.Z = null;
    Z.Le = function (a, c) {
        function d(a) {
            a = a.split("-");
            for (var c = [], d = 0; d < a.length; d++)c.push(e(a[d]));
            return c.join("-")
        }

        function e(a) {
            return Z.jb[a] || a
        }

        var f;
        f = Z.jb ? "BY_WHOLE" == Z.Cc ? e : d : function (a) {
            return a
        };
        return c ? a + "-" + f(c) : f(a)
    };
    Z.dg = function (a, c) {
        Z.jb = a;
        Z.Cc = c
    };
    Z.Se = function (a, c) {
        c && (a = a.replace(/\{\$([^}]+)}/g, function (a, e) {
            return null != c && e in c ? c[e] : a
        }));
        return a
    };
    Z.Te = function (a) {
        return a
    };
    Z.Ba = function (a, c) {
        Z.Aa(a, c, void 0)
    };
    Z.Ie = function (a, c, d) {
        a[c] = d
    };
    Z.Ja = function (a, c) {
        function d() {
        }

        d.prototype = c.prototype;
        a.qa = c.prototype;
        a.prototype = new d;
        a.prototype.constructor = a;
        a.wc = function (a, d, g) {
            for (var h = Array(arguments.length - 2), l = 2; l < arguments.length; l++)h[l - 2] = arguments[l];
            return c.prototype[d].apply(a, h)
        }
    };
    Z.wc = function (a, c, d) {
        var e = arguments.callee.caller;
        if (Z.ic || Z.H && !e)throw Error("arguments.caller not defined.  goog.base() cannot be used with strict mode code. See http://www.ecma-international.org/ecma-262/5.1/#sec-C");
        if (e.qa) {
            for (var f = Array(arguments.length - 1), g = 1; g < arguments.length; g++)f[g - 1] = arguments[g];
            return e.qa.constructor.apply(a, f)
        }
        f = Array(arguments.length - 2);
        for (g = 2; g < arguments.length; g++)f[g - 2] = arguments[g];
        for (var g = !1, h = a.constructor; h; h = h.qa && h.qa.constructor)if (h.prototype[c] ===
            e)g = !0; else if (g)return h.prototype[c].apply(a, f);
        if (a[c] === e)return a.constructor.prototype[c].apply(a, f);
        throw Error("goog.base called from a method of one name to a method of a different name");
    };
    Z.scope = function (a) {
        if (Z.Ma())throw Error("goog.scope is not supported within a goog.module.");
        a.call(Z.global)
    };
    Z.o = function (a, c) {
        var d = c.constructor, e = c.md;
        d && d != Object.prototype.constructor || (d = function () {
            throw Error("cannot instantiate an interface (no constructor defined).");
        });
        d = Z.o.Ac(d, a);
        a && Z.Ja(d, a);
        delete c.constructor;
        delete c.md;
        Z.o.eb(d.prototype, c);
        null != e && (e instanceof Function ? e(d) : Z.o.eb(d, e));
        return d
    };
    Z.o.fc = Z.H;
    Z.o.Ac = function (a, c) {
        function d() {
            var c = a.apply(this, arguments) || this;
            c[Z.F] = c[Z.F];
            this.constructor === d && e && Object.seal instanceof Function && Object.seal(c);
            return c
        }

        if (!Z.o.fc)return a;
        var e = !Z.o.Sc(c);
        return d
    };
    Z.o.Sc = function (a) {
        return a && a.prototype && a.prototype[Z.qc]
    };
    Z.o.bb = "constructor hasOwnProperty isPrototypeOf propertyIsEnumerable toLocaleString toString valueOf".split(" ");
    Z.o.eb = function (a, c) {
        for (var d in c)Object.prototype.hasOwnProperty.call(c, d) && (a[d] = c[d]);
        for (var e = 0; e < Z.o.bb.length; e++)d = Z.o.bb[e], Object.prototype.hasOwnProperty.call(c, d) && (a[d] = c[d])
    };
    Z.vg = Y();
    Z.qc = "goog_defineClass_legacy_unsealable";
    Z.debug = {};
    Z.debug.Error = function (a) {
        if (Error.captureStackTrace)Error.captureStackTrace(this, Z.debug.Error); else {
            var c = Error().stack;
            c && (this.stack = c)
        }
        a && (this.message = String(a))
    };
    Z.Ja(Z.debug.Error, Error);
    Z.debug.Error.prototype.name = "CustomError";
    Z.kb = {};
    Z.kb.dc = {Tb: 1, yd: 2, Od: 3, zd: 4, Jd: 5, Id: 6, Nd: 7, Cd: 8, Ed: 9, Gd: 10, Fd: 11, Ld: 12};
    Z.c = {};
    Z.c.Za = !1;
    Z.c.Vb = !1;
    Z.c.rc = {bc: "\u00a0"};
    Z.c.startsWith = function (a, c) {
        return 0 == a.lastIndexOf(c, 0)
    };
    Z.c.endsWith = function (a, c) {
        var d = a.length - c.length;
        return 0 <= d && a.indexOf(c, d) == d
    };
    Z.c.ue = function (a, c) {
        return 0 == Z.c.hb(c, a.substr(0, c.length))
    };
    Z.c.qe = function (a, c) {
        return 0 == Z.c.hb(c, a.substr(a.length - c.length, c.length))
    };
    Z.c.re = function (a, c) {
        return a.toLowerCase() == c.toLowerCase()
    };
    Z.c.od = function (a, c) {
        for (var d = a.split("%s"), e = "", f = Array.prototype.slice.call(arguments, 1); f.length && 1 < d.length;)e += d.shift() + f.shift();
        return e + d.join("%s")
    };
    Z.c.xe = function (a) {
        return a.replace(/[\s\xa0]+/g, " ").replace(/^\s+|\s+$/g, "")
    };
    Z.c.La = function (a) {
        return /^[\s\xa0]*$/.test(a)
    };
    Z.c.pf = function (a) {
        return 0 == a.length
    };
    Z.c.Oc = Z.c.La;
    Z.c.Pc = function (a) {
        return Z.c.La(Z.c.ad(a))
    };
    Z.c.nf = Z.c.Pc;
    Z.c.lf = function (a) {
        return !/[^\t\n\r ]/.test(a)
    };
    Z.c.jf = function (a) {
        return !/[^a-zA-Z]/.test(a)
    };
    Z.c.sf = function (a) {
        return !/[^0-9]/.test(a)
    };
    Z.c.kf = function (a) {
        return !/[^a-zA-Z0-9]/.test(a)
    };
    Z.c.vf = function (a) {
        return " " == a
    };
    Z.c.wf = function (a) {
        return 1 == a.length && " " <= a && "~" >= a || "\u0080" <= a && "\ufffd" >= a
    };
    Z.c.qg = function (a) {
        return a.replace(/(\r\n|\r|\n)+/g, " ")
    };
    Z.c.me = function (a) {
        return a.replace(/(\r\n|\r|\n)/g, "\n")
    };
    Z.c.Hf = function (a) {
        return a.replace(/\xa0|\s/g, " ")
    };
    Z.c.Gf = function (a) {
        return a.replace(/\xa0|[ \t]+/g, " ")
    };
    Z.c.we = function (a) {
        return a.replace(/[\t\r\n ]+/g, " ").replace(/^[\t\r\n ]+|[\t\r\n ]+$/g, "")
    };
    Z.c.trim = Z.ra && String.prototype.trim ? function (a) {
        return a.trim()
    } : function (a) {
        return a.replace(/^[\s\xa0]+|[\s\xa0]+$/g, "")
    };
    Z.c.trimLeft = function (a) {
        return a.replace(/^[\s\xa0]+/, "")
    };
    Z.c.trimRight = function (a) {
        return a.replace(/[\s\xa0]+$/, "")
    };
    Z.c.hb = function (a, c) {
        a = String(a).toLowerCase();
        c = String(c).toLowerCase();
        return a < c ? -1 : a == c ? 0 : 1
    };
    Z.c.Gb = function (a, c, d) {
        if (a == c)return 0;
        if (!a)return -1;
        if (!c)return 1;
        for (var e = a.toLowerCase().match(d), f = c.toLowerCase().match(d), g = Math.min(e.length, f.length), h = 0; h < g; h++) {
            d = e[h];
            var l = f[h];
            if (d != l)return a = parseInt(d, 10), !isNaN(a) && (c = parseInt(l, 10), !isNaN(c) && a - c) ? a - c : d < l ? -1 : 1
        }
        return e.length != f.length ? e.length - f.length : a < c ? -1 : 1
    };
    Z.c.gf = function (a, c) {
        return Z.c.Gb(a, c, /\d+|\D+/g)
    };
    Z.c.Ic = function (a, c) {
        return Z.c.Gb(a, c, /\d+|\.\d+|\D+/g)
    };
    Z.c.Jf = Z.c.Ic;
    Z.c.Fg = function (a) {
        return encodeURIComponent(String(a))
    };
    Z.c.Eg = function (a) {
        return decodeURIComponent(a.replace(/\+/g, " "))
    };
    Z.c.bd = function (a, c) {
        return a.replace(/(\r\n|\r|\n)/g, c ? "<br />" : "<br>")
    };
    Z.c.tb = function (a) {
        if (!Z.c.Nb.test(a))return a;
        -1 != a.indexOf("&") && (a = a.replace(Z.c.Ob, "&amp;"));
        -1 != a.indexOf("<") && (a = a.replace(Z.c.ac, "&lt;"));
        -1 != a.indexOf(">") && (a = a.replace(Z.c.Wb, "&gt;"));
        -1 != a.indexOf('"') && (a = a.replace(Z.c.ec, "&quot;"));
        -1 != a.indexOf("'") && (a = a.replace(Z.c.hc, "&#39;"));
        -1 != a.indexOf("\x00") && (a = a.replace(Z.c.cc, "&#0;"));
        Z.c.Za && -1 != a.indexOf("e") && (a = a.replace(Z.c.Ub, "&#101;"));
        return a
    };
    Z.c.Ob = /&/g;
    Z.c.ac = /</g;
    Z.c.Wb = />/g;
    Z.c.ec = /"/g;
    Z.c.hc = /'/g;
    Z.c.cc = /\x00/g;
    Z.c.Ub = /e/g;
    Z.c.Nb = Z.c.Za ? /[\x00&<>"'e]/ : /[\x00&<>"']/;
    Z.c.Jb = function (a) {
        return Z.c.contains(a, "&") ? !Z.c.Vb && "document" in Z.global ? Z.c.Kb(a) : Z.c.td(a) : a
    };
    Z.c.Dg = function (a, c) {
        return Z.c.contains(a, "&") ? Z.c.Kb(a, c) : a
    };
    Z.c.Kb = function (a, c) {
        var d = {"&amp;": "&", "&lt;": "<", "&gt;": ">", "&quot;": '"'}, e;
        e = c ? c.createElement("div") : Z.global.document.createElement("div");
        return a.replace(Z.c.Xb, function (a, c) {
            var h = d[a];
            if (h)return h;
            "#" == c.charAt(0) && (c = Number("0" + c.substr(1)), isNaN(c) || (h = String.fromCharCode(c)));
            h || (e.innerHTML = a + " ", h = e.firstChild.nodeValue.slice(0, -1));
            return d[a] = h
        })
    };
    Z.c.td = function (a) {
        return a.replace(/&([^;]+);/g, function (a, d) {
            switch (d) {
                case "amp":
                    return "&";
                case "lt":
                    return "<";
                case "gt":
                    return ">";
                case "quot":
                    return '"';
                default:
                    return "#" != d.charAt(0) || (d = Number("0" + d.substr(1)), isNaN(d)) ? a : String.fromCharCode(d)
            }
        })
    };
    Z.c.Xb = /&([^;\s<&]+);?/g;
    Z.c.Hg = function (a, c) {
        return Z.c.bd(a.replace(/  /g, " &#160;"), c)
    };
    Z.c.Mf = function (a) {
        return a.replace(/(^|[\n ]) /g, "$1" + Z.c.rc.bc)
    };
    Z.c.rg = function (a, c) {
        for (var d = c.length, e = 0; e < d; e++) {
            var f = 1 == d ? c : c.charAt(e);
            if (a.charAt(0) == f && a.charAt(a.length - 1) == f)return a.substring(1, a.length - 1)
        }
        return a
    };
    Z.c.truncate = function (a, c, d) {
        d && (a = Z.c.Jb(a));
        a.length > c && (a = a.substring(0, c - 3) + "...");
        d && (a = Z.c.tb(a));
        return a
    };
    Z.c.Cg = function (a, c, d, e) {
        d && (a = Z.c.Jb(a));
        e && a.length > c ? (e > c && (e = c), a = a.substring(0, c - e) + "..." + a.substring(a.length - e)) : a.length > c && (e = Math.floor(c / 2), a = a.substring(0, e + c % 2) + "..." + a.substring(a.length - e));
        d && (a = Z.c.tb(a));
        return a
    };
    Z.c.Wa = {
        "\x00": "\\0",
        "\b": "\\b",
        "\f": "\\f",
        "\n": "\\n",
        "\r": "\\r",
        "\t": "\\t",
        "\x0B": "\\x0B",
        '"': '\\"',
        "\\": "\\\\",
        "<": "<"
    };
    Z.c.ma = {"'": "\\'"};
    Z.c.quote = function (a) {
        a = String(a);
        for (var c = ['"'], d = 0; d < a.length; d++) {
            var e = a.charAt(d), f = e.charCodeAt(0);
            c[d + 1] = Z.c.Wa[e] || (31 < f && 127 > f ? e : Z.c.mb(e))
        }
        c.push('"');
        return c.join("")
    };
    Z.c.He = function (a) {
        for (var c = [], d = 0; d < a.length; d++)c[d] = Z.c.mb(a.charAt(d));
        return c.join("")
    };
    Z.c.mb = function (a) {
        if (a in Z.c.ma)return Z.c.ma[a];
        if (a in Z.c.Wa)return Z.c.ma[a] = Z.c.Wa[a];
        var c, d = a.charCodeAt(0);
        if (31 < d && 127 > d)c = a; else {
            if (256 > d) {
                if (c = "\\x", 16 > d || 256 < d)c += "0"
            } else c = "\\u", 4096 > d && (c += "0");
            c += d.toString(16).toUpperCase()
        }
        return Z.c.ma[a] = c
    };
    Z.c.contains = function (a, c) {
        return -1 != a.indexOf(c)
    };
    Z.c.pe = function (a, c) {
        return Z.c.contains(a.toLowerCase(), c.toLowerCase())
    };
    Z.c.Ce = function (a, c) {
        return a && c ? a.split(c).length - 1 : 0
    };
    Z.c.M = function (a, c, d) {
        var e = a;
        0 <= c && c < a.length && 0 < d && (e = a.substr(0, c) + a.substr(c + d, a.length - c - d));
        return e
    };
    Z.c.remove = function (a, c) {
        c = new RegExp(Z.c.Ua(c), "");
        return a.replace(c, "")
    };
    Z.c.Vf = function (a, c) {
        c = new RegExp(Z.c.Ua(c), "g");
        return a.replace(c, "")
    };
    Z.c.Ua = function (a) {
        return String(a).replace(/([-()\[\]{}+?*.$\^|,:#<!\\])/g, "\\$1").replace(/\x08/g, "\\x08")
    };
    Z.c.repeat = String.prototype.repeat ? function (a, c) {
        return a.repeat(c)
    } : function (a, c) {
        return Array(c + 1).join(a)
    };
    Z.c.Lf = function (a, c, d) {
        a = Z.S(d) ? a.toFixed(d) : String(a);
        d = a.indexOf(".");
        -1 == d && (d = a.length);
        return Z.c.repeat("0", Math.max(0, c - d)) + a
    };
    Z.c.ad = function (a) {
        return null == a ? "" : String(a)
    };
    Z.c.le = function (a) {
        return Array.prototype.join.call(arguments, "")
    };
    Z.c.We = function () {
        return Math.floor(2147483648 * Math.random()).toString(36) + Math.abs(Math.floor(2147483648 * Math.random()) ^ Z.now()).toString(36)
    };
    Z.c.ze = function (a, c) {
        var d = 0;
        a = Z.c.trim(String(a)).split(".");
        c = Z.c.trim(String(c)).split(".");
        for (var e = Math.max(a.length, c.length), f = 0; 0 == d && f < e; f++) {
            var g = a[f] || "", h = c[f] || "", l = /(\d*)(\D*)/g, m = /(\d*)(\D*)/g;
            do {
                var n = l.exec(g) || ["", "", ""], w = m.exec(h) || ["", "", ""];
                if (0 == n[0].length && 0 == w[0].length)break;
                d = Z.c.ua(0 == n[1].length ? 0 : parseInt(n[1], 10), 0 == w[1].length ? 0 : parseInt(w[1], 10)) || Z.c.ua(0 == n[2].length, 0 == w[2].length) || Z.c.ua(n[2], w[2])
            } while (0 == d)
        }
        return d
    };
    Z.c.ua = function (a, c) {
        return a < c ? -1 : a > c ? 1 : 0
    };
    Z.c.cf = function (a) {
        for (var c = 0, d = 0; d < a.length; ++d)c = 31 * c + a.charCodeAt(d) >>> 0;
        return c
    };
    Z.c.ud = 2147483648 * Math.random() | 0;
    Z.c.De = function () {
        return "goog_" + Z.c.ud++
    };
    Z.c.xg = function (a) {
        var c = Number(a);
        return 0 == c && Z.c.La(a) ? NaN : c
    };
    Z.c.qf = function (a) {
        return /^[a-z]+([A-Z][a-z]*)*$/.test(a)
    };
    Z.c.xf = function (a) {
        return /^([A-Z][a-z]*)+$/.test(a)
    };
    Z.c.wg = function (a) {
        return String(a).replace(/\-([a-z])/g, function (a, d) {
            return d.toUpperCase()
        })
    };
    Z.c.zg = function (a) {
        return String(a).replace(/([A-Z])/g, "-$1").toLowerCase()
    };
    Z.c.Ag = function (a, c) {
        c = Z.h(c) ? Z.c.Ua(c) : "\\s";
        return a.replace(new RegExp("(^" + (c ? "|[" + c + "]+" : "") + ")([a-z])", "g"), function (a, c, f) {
            return c + f.toUpperCase()
        })
    };
    Z.c.oe = function (a) {
        return String(a.charAt(0)).toUpperCase() + String(a.substr(1)).toLowerCase()
    };
    Z.c.parseInt = function (a) {
        isFinite(a) && (a = String(a));
        return Z.h(a) ? /^\s*-?0x/i.test(a) ? parseInt(a, 16) : parseInt(a, 10) : NaN
    };
    Z.c.og = function (a, c, d) {
        a = a.split(c);
        for (var e = []; 0 < d && a.length;)e.push(a.shift()), d--;
        a.length && e.push(a.join(c));
        return e
    };
    Z.c.zf = function (a, c) {
        if (c)"string" == typeof c && (c = [c]); else return a;
        for (var d = -1, e = 0; e < c.length; e++)if ("" != c[e]) {
            var f = a.lastIndexOf(c[e]);
            f > d && (d = f)
        }
        return -1 == d ? a : a.slice(d + 1)
    };
    Z.c.Fe = function (a, c) {
        var d = [], e = [];
        if (a == c)return 0;
        if (!a.length || !c.length)return Math.max(a.length, c.length);
        for (var f = 0; f < c.length + 1; f++)d[f] = f;
        for (f = 0; f < a.length; f++) {
            e[0] = f + 1;
            for (var g = 0; g < c.length; g++)e[g + 1] = Math.min(e[g] + 1, d[g + 1] + 1, d[g] + Number(a[f] != c[g]));
            for (g = 0; g < d.length; g++)d[g] = e[g]
        }
        return e[c.length]
    };
    Z.g = {};
    Z.g.s = Z.H;
    Z.g.W = function (a, c) {
        c.unshift(a);
        Z.debug.Error.call(this, Z.c.od.apply(null, c));
        c.shift()
    };
    Z.Ja(Z.g.W, Z.debug.Error);
    Z.g.W.prototype.name = "AssertionError";
    Z.g.Rb = function (a) {
        throw a;
    };
    Z.g.ya = Z.g.Rb;
    Z.g.A = function (a, c, d, e) {
        var f = "Assertion failed";
        if (d)var f = f + (": " + d), g = e; else a && (f += ": " + a, g = c);
        a = new Z.g.W("" + f, g || []);
        Z.g.ya(a)
    };
    Z.g.eg = function (a) {
        Z.g.s && (Z.g.ya = a)
    };
    Z.g.assert = function (a, c, d) {
        Z.g.s && !a && Z.g.A("", null, c, Array.prototype.slice.call(arguments, 2));
        return a
    };
    Z.g.Ca = function (a, c) {
        Z.g.s && Z.g.ya(new Z.g.W("Failure" + (a ? ": " + a : ""), Array.prototype.slice.call(arguments, 1)))
    };
    Z.g.Xd = function (a, c, d) {
        Z.g.s && !Z.Qc(a) && Z.g.A("Expected number but got %s: %s.", [Z.u(a), a], c, Array.prototype.slice.call(arguments, 2));
        return a
    };
    Z.g.$d = function (a, c, d) {
        Z.g.s && !Z.h(a) && Z.g.A("Expected string but got %s: %s.", [Z.u(a), a], c, Array.prototype.slice.call(arguments, 2));
        return a
    };
    Z.g.Vd = function (a, c, d) {
        Z.g.s && !Z.Ab(a) && Z.g.A("Expected function but got %s: %s.", [Z.u(a), a], c, Array.prototype.slice.call(arguments, 2));
        return a
    };
    Z.g.Yd = function (a, c, d) {
        Z.g.s && !Z.ea(a) && Z.g.A("Expected object but got %s: %s.", [Z.u(a), a], c, Array.prototype.slice.call(arguments, 2));
        return a
    };
    Z.g.Sd = function (a, c, d) {
        Z.g.s && !Z.isArray(a) && Z.g.A("Expected array but got %s: %s.", [Z.u(a), a], c, Array.prototype.slice.call(arguments, 2));
        return a
    };
    Z.g.Td = function (a, c, d) {
        Z.g.s && !Z.Mc(a) && Z.g.A("Expected boolean but got %s: %s.", [Z.u(a), a], c, Array.prototype.slice.call(arguments, 2));
        return a
    };
    Z.g.Ud = function (a, c, d) {
        !Z.g.s || Z.ea(a) && a.nodeType == Z.kb.dc.Tb || Z.g.A("Expected Element but got %s: %s.", [Z.u(a), a], c, Array.prototype.slice.call(arguments, 2));
        return a
    };
    Z.g.Wd = function (a, c, d, e) {
        !Z.g.s || a instanceof c || Z.g.A("Expected instanceof %s but got %s.", [Z.g.rb(c), Z.g.rb(a)], d, Array.prototype.slice.call(arguments, 3));
        return a
    };
    Z.g.Zd = function () {
        for (var a in Object.prototype)Z.g.Ca(a + " should not be enumerable in Object.prototype.")
    };
    Z.g.rb = function (a) {
        return a instanceof Function ? a.displayName || a.name || "unknown type name" : a instanceof Object ? a.constructor.displayName || a.constructor.name || Object.prototype.toString.call(a) : null === a ? "null" : typeof a
    };
    Z.f = {};
    Z.w = Z.ra;
    Z.f.v = !1;
    Z.f.ed = function (a) {
        return a[a.length - 1]
    };
    Z.f.yf = Z.f.ed;
    Z.f.indexOf = Z.w && (Z.f.v || Array.prototype.indexOf) ? function (a, c, d) {
        return Array.prototype.indexOf.call(a, c, d)
    } : function (a, c, d) {
        d = null == d ? 0 : 0 > d ? Math.max(0, a.length + d) : d;
        if (Z.h(a))return Z.h(c) && 1 == c.length ? a.indexOf(c, d) : -1;
        for (; d < a.length; d++)if (d in a && a[d] === c)return d;
        return -1
    };
    Z.f.lastIndexOf = Z.w && (Z.f.v || Array.prototype.lastIndexOf) ? function (a, c, d) {
        return Array.prototype.lastIndexOf.call(a, c, null == d ? a.length - 1 : d)
    } : function (a, c, d) {
        d = null == d ? a.length - 1 : d;
        0 > d && (d = Math.max(0, a.length + d));
        if (Z.h(a))return Z.h(c) && 1 == c.length ? a.lastIndexOf(c, d) : -1;
        for (; 0 <= d; d--)if (d in a && a[d] === c)return d;
        return -1
    };
    Z.f.forEach = Z.w && (Z.f.v || Array.prototype.forEach) ? function (a, c, d) {
        Array.prototype.forEach.call(a, c, d)
    } : function (a, c, d) {
        for (var e = a.length, f = Z.h(a) ? a.split("") : a, g = 0; g < e; g++)g in f && c.call(d, f[g], g, a)
    };
    Z.f.ob = function (a, c) {
        for (var d = Z.h(a) ? a.split("") : a, e = a.length - 1; 0 <= e; --e)e in d && c.call(void 0, d[e], e, a)
    };
    Z.f.filter = Z.w && (Z.f.v || Array.prototype.filter) ? function (a, c, d) {
        return Array.prototype.filter.call(a, c, d)
    } : function (a, c, d) {
        for (var e = a.length, f = [], g = 0, h = Z.h(a) ? a.split("") : a, l = 0; l < e; l++)if (l in h) {
            var m = h[l];
            c.call(d, m, l, a) && (f[g++] = m)
        }
        return f
    };
    Z.f.map = Z.w && (Z.f.v || Array.prototype.map) ? function (a, c, d) {
        return Array.prototype.map.call(a, c, d)
    } : function (a, c, d) {
        for (var e = a.length, f = Array(e), g = Z.h(a) ? a.split("") : a, h = 0; h < e; h++)h in g && (f[h] = c.call(d, g[h], h, a));
        return f
    };
    Z.f.reduce = Z.w && (Z.f.v || Array.prototype.reduce) ? function (a, c, d, e) {
        e && (c = Z.bind(c, e));
        return Array.prototype.reduce.call(a, c, d)
    } : function (a, c, d, e) {
        var f = d;
        Z.f.forEach(a, function (d, h) {
            f = c.call(e, f, d, h, a)
        });
        return f
    };
    Z.f.reduceRight = Z.w && (Z.f.v || Array.prototype.reduceRight) ? function (a, c, d, e) {
        e && (c = Z.bind(c, e));
        return Array.prototype.reduceRight.call(a, c, d)
    } : function (a, c, d, e) {
        var f = d;
        Z.f.ob(a, function (d, h) {
            f = c.call(e, f, d, h, a)
        });
        return f
    };
    Z.f.some = Z.w && (Z.f.v || Array.prototype.some) ? function (a, c, d) {
        return Array.prototype.some.call(a, c, d)
    } : function (a, c, d) {
        for (var e = a.length, f = Z.h(a) ? a.split("") : a, g = 0; g < e; g++)if (g in f && c.call(d, f[g], g, a))return !0;
        return !1
    };
    Z.f.every = Z.w && (Z.f.v || Array.prototype.every) ? function (a, c, d) {
        return Array.prototype.every.call(a, c, d)
    } : function (a, c, d) {
        for (var e = a.length, f = Z.h(a) ? a.split("") : a, g = 0; g < e; g++)if (g in f && !c.call(d, f[g], g, a))return !1;
        return !0
    };
    Z.f.count = function (a, c, d) {
        var e = 0;
        Z.f.forEach(a, function (a, g, h) {
            c.call(d, a, g, h) && ++e
        }, d);
        return e
    };
    Z.f.find = function (a, c, d) {
        c = Z.f.findIndex(a, c, d);
        return 0 > c ? null : Z.h(a) ? a.charAt(c) : a[c]
    };
    Z.f.findIndex = function (a, c, d) {
        for (var e = a.length, f = Z.h(a) ? a.split("") : a, g = 0; g < e; g++)if (g in f && c.call(d, f[g], g, a))return g;
        return -1
    };
    Z.f.Je = function (a, c, d) {
        c = Z.f.Gc(a, c, d);
        return 0 > c ? null : Z.h(a) ? a.charAt(c) : a[c]
    };
    Z.f.Gc = function (a, c, d) {
        for (var e = Z.h(a) ? a.split("") : a, f = a.length - 1; 0 <= f; f--)if (f in e && c.call(d, e[f], f, a))return f;
        return -1
    };
    Z.f.contains = function (a, c) {
        return 0 <= Z.f.indexOf(a, c)
    };
    Z.f.Oc = function (a) {
        return 0 == a.length
    };
    Z.f.clear = function (a) {
        if (!Z.isArray(a))for (var c = a.length - 1; 0 <= c; c--)delete a[c];
        a.length = 0
    };
    Z.f.df = function (a, c) {
        Z.f.contains(a, c) || a.push(c)
    };
    Z.f.wb = function (a, c, d) {
        Z.f.splice(a, d, 0, c)
    };
    Z.f.ef = function (a, c, d) {
        Z.dd(Z.f.splice, a, d, 0).apply(null, c)
    };
    Z.f.insertBefore = function (a, c, d) {
        var e;
        2 == arguments.length || 0 > (e = Z.f.indexOf(a, d)) ? a.push(c) : Z.f.wb(a, c, e)
    };
    Z.f.remove = function (a, c) {
        c = Z.f.indexOf(a, c);
        var d;
        (d = 0 <= c) && Z.f.M(a, c);
        return d
    };
    Z.f.$f = function (a, c) {
        c = Z.f.lastIndexOf(a, c);
        return 0 <= c ? (Z.f.M(a, c), !0) : !1
    };
    Z.f.M = function (a, c) {
        return 1 == Array.prototype.splice.call(a, c, 1).length
    };
    Z.f.Zf = function (a, c, d) {
        c = Z.f.findIndex(a, c, d);
        return 0 <= c ? (Z.f.M(a, c), !0) : !1
    };
    Z.f.Wf = function (a, c, d) {
        var e = 0;
        Z.f.ob(a, function (f, g) {
            c.call(d, f, g, a) && Z.f.M(a, g) && e++
        });
        return e
    };
    Z.f.concat = function (a) {
        return Array.prototype.concat.apply(Array.prototype, arguments)
    };
    Z.f.join = function (a) {
        return Array.prototype.concat.apply(Array.prototype, arguments)
    };
    Z.f.qd = function (a) {
        var c = a.length;
        if (0 < c) {
            for (var d = Array(c), e = 0; e < c; e++)d[e] = a[e];
            return d
        }
        return []
    };
    Z.f.clone = Z.f.qd;
    Z.f.extend = function (a, c) {
        for (var d = 1; d < arguments.length; d++) {
            var e = arguments[d];
            if (Z.Ka(e)) {
                var f = a.length || 0, g = e.length || 0;
                a.length = f + g;
                for (var h = 0; h < g; h++)a[f + h] = e[h]
            } else a.push(e)
        }
    };
    Z.f.splice = function (a, c, d, e) {
        return Array.prototype.splice.apply(a, Z.f.slice(arguments, 1))
    };
    Z.f.slice = function (a, c, d) {
        return 2 >= arguments.length ? Array.prototype.slice.call(a, c) : Array.prototype.slice.call(a, c, d)
    };
    Z.f.Xf = function (a, c, d) {
        function e(a) {
            return Z.ea(a) ? "o" + Z.sb(a) : (typeof a).charAt(0) + a
        }

        c = c || a;
        d = d || e;
        for (var f = {}, g = 0, h = 0; h < a.length;) {
            var l = a[h++], m = d(l);
            Object.prototype.hasOwnProperty.call(f, m) || (f[m] = !0, c[g++] = l)
        }
        c.length = g
    };
    Z.f.fb = function (a, c, d) {
        return Z.f.gb(a, d || Z.f.C, !1, c)
    };
    Z.f.je = function (a, c, d) {
        return Z.f.gb(a, c, !0, void 0, d)
    };
    Z.f.gb = function (a, c, d, e, f) {
        for (var g = 0, h = a.length, l; g < h;) {
            var m = g + h >> 1, n;
            n = d ? c.call(f, a[m], m, a) : c(e, a[m]);
            0 < n ? g = m + 1 : (h = m, l = !n)
        }
        return l ? g : ~g
    };
    Z.f.sort = function (a, c) {
        a.sort(c || Z.f.C)
    };
    Z.f.pg = function (a, c) {
        for (var d = Array(a.length), e = 0; e < a.length; e++)d[e] = {index: e, value: a[e]};
        var f = c || Z.f.C;
        Z.f.sort(d, function (a, c) {
            return f(a.value, c.value) || a.index - c.index
        });
        for (e = 0; e < a.length; e++)a[e] = d[e].value
    };
    Z.f.ld = function (a, c, d) {
        var e = d || Z.f.C;
        Z.f.sort(a, function (a, d) {
            return e(c(a), c(d))
        })
    };
    Z.f.jg = function (a, c, d) {
        Z.f.ld(a, function (a) {
            return a[c]
        }, d)
    };
    Z.f.uf = function (a, c, d) {
        c = c || Z.f.C;
        for (var e = 1; e < a.length; e++) {
            var f = c(a[e - 1], a[e]);
            if (0 < f || 0 == f && d)return !1
        }
        return !0
    };
    Z.f.Ge = function (a, c, d) {
        if (!Z.Ka(a) || !Z.Ka(c) || a.length != c.length)return !1;
        var e = a.length;
        d = d || Z.f.Dc;
        for (var f = 0; f < e; f++)if (!d(a[f], c[f]))return !1;
        return !0
    };
    Z.f.ye = function (a, c, d) {
        d = d || Z.f.C;
        for (var e = Math.min(a.length, c.length), f = 0; f < e; f++) {
            var g = d(a[f], c[f]);
            if (0 != g)return g
        }
        return Z.f.C(a.length, c.length)
    };
    Z.f.C = function (a, c) {
        return a > c ? 1 : a < c ? -1 : 0
    };
    Z.f.hf = function (a, c) {
        return -Z.f.C(a, c)
    };
    Z.f.Dc = function (a, c) {
        return a === c
    };
    Z.f.fe = function (a, c, d) {
        d = Z.f.fb(a, c, d);
        return 0 > d ? (Z.f.wb(a, c, -(d + 1)), !0) : !1
    };
    Z.f.ge = function (a, c, d) {
        c = Z.f.fb(a, c, d);
        return 0 <= c ? Z.f.M(a, c) : !1
    };
    Z.f.ke = function (a, c, d) {
        for (var e = {}, f = 0; f < a.length; f++) {
            var g = a[f], h = c.call(d, g, f, a);
            Z.S(h) && (e[h] || (e[h] = [])).push(g)
        }
        return e
    };
    Z.f.yg = function (a, c, d) {
        var e = {};
        Z.f.forEach(a, function (f, g) {
            e[c.call(d, f, g, a)] = f
        });
        return e
    };
    Z.f.Of = function (a, c, d) {
        var e = [], f = 0, g = a;
        d = d || 1;
        void 0 !== c && (f = a, g = c);
        if (0 > d * (g - f))return [];
        if (0 < d)for (a = f; a < g; a += d)e.push(a); else for (a = f; a > g; a += d)e.push(a);
        return e
    };
    Z.f.repeat = function (a, c) {
        for (var d = [], e = 0; e < c; e++)d[e] = a;
        return d
    };
    Z.f.Hc = function (a) {
        for (var c = [], d = 0; d < arguments.length; d++) {
            var e = arguments[d];
            if (Z.isArray(e))for (var f = 0; f < e.length; f += 8192)for (var g = Z.f.Hc.apply(null, Z.f.slice(e, f, f + 8192)), h = 0; h < g.length; h++)c.push(g[h]); else c.push(e)
        }
        return c
    };
    Z.f.rotate = function (a, c) {
        a.length && (c %= a.length, 0 < c ? Array.prototype.unshift.apply(a, a.splice(-c, c)) : 0 > c && Array.prototype.push.apply(a, a.splice(0, -c)));
        return a
    };
    Z.f.Ef = function (a, c, d) {
        c = Array.prototype.splice.call(a, c, 1);
        Array.prototype.splice.call(a, d, 0, c[0])
    };
    Z.f.Kg = function (a) {
        if (!arguments.length)return [];
        for (var c = [], d = arguments[0].length, e = 1; e < arguments.length; e++)arguments[e].length < d && (d = arguments[e].length);
        for (e = 0; e < d; e++) {
            for (var f = [], g = 0; g < arguments.length; g++)f.push(arguments[g][e]);
            c.push(f)
        }
        return c
    };
    Z.f.hg = function (a, c) {
        c = c || Math.random;
        for (var d = a.length - 1; 0 < d; d--) {
            var e = Math.floor(c() * (d + 1)), f = a[d];
            a[d] = a[e];
            a[e] = f
        }
    };
    Z.f.Be = function (a, c) {
        var d = [];
        Z.f.forEach(c, function (c) {
            d.push(a[c])
        });
        return d
    };
    Z.f.Ae = function (a, c, d) {
        return Z.f.concat.apply([], Z.f.map(a, c, d))
    };
    Z.locale = {};
    Z.locale.K = {
        COUNTRY: {
            AD: "Andorra",
            AE: hb,
            AF: T,
            AG: ca,
            AI: "Anguilla",
            AL: "Shqip\u00ebria",
            AM: gb,
            AN: qa,
            AO: "Angola",
            AQ: "Antarctica",
            AR: "Argentina",
            AS: b,
            AT: "\u00d6sterreich",
            AU: "Australia",
            AW: "Aruba",
            AX: "\u00c5land",
            AZ: "Az\u0259rbaycan",
            BA: p,
            BB: "Barbados",
            BD: "\u09ac\u09be\u0982\u09b2\u09be\u09a6\u09c7\u09b6",
            BE: "Belgi\u00eb",
            BF: "Burkina Faso",
            BG: "\u0411\u044a\u043b\u0433\u0430\u0440\u0438\u044f",
            BH: "\u0627\u0644\u0628\u062d\u0631\u064a\u0646",
            BI: "Burundi",
            BJ: "B\u00e9nin",
            BM: "Bermuda",
            BN: "Brunei",
            BO: k,
            BR: "Brasil",
            BS: "Bahamas",
            BT: "\u092d\u0942\u091f\u093e\u0928",
            BV: "Bouvet Island",
            BW: q,
            BY: db,
            BZ: "Belize",
            CA: "Canada",
            CC: "Cocos (Keeling) Islands",
            CD: Ca,
            CF: Ba,
            CG: "Congo",
            CH: "Schweiz",
            CI: "C\u00f4te d\u2019Ivoire",
            CK: "Cook Islands",
            CL: "Chile",
            CM: "Cameroun",
            CN: "\u4e2d\u56fd",
            CO: "Colombia",
            CR: "Costa Rica",
            CS: Ga,
            CU: "Cuba",
            CV: "Cabo Verde",
            CX: fa,
            CY: "\u039a\u03cd\u03c0\u03c1\u03bf\u03c2",
            CZ: cb,
            DD: "East Germany",
            DE: "Deutschland",
            DJ: "Jabuuti",
            DK: "Danmark",
            DM: "Dominica",
            DO: za,
            DZ: "\u0627\u0644\u062c\u0632\u0627\u0626\u0631",
            EC: "Ecuador",
            EE: "Eesti",
            EG: "\u0645\u0635\u0631",
            EH: ib,
            ER: "\u0627\u0631\u064a\u062a\u0631\u064a\u0627",
            ES: "Espa\u00f1a",
            ET: X,
            FI: "Suomi",
            FJ: "\u092b\u093f\u091c\u0940",
            FK: ga,
            FM: x,
            FO: "F\u00f8royar",
            FR: "France",
            FX: "Metropolitan France",
            GA: "Gabon",
            GB: Ra,
            GD: "Grenada",
            GE: "\u10e1\u10d0\u10e5\u10d0\u10e0\u10d7\u10d5\u10d4\u10da\u10dd",
            GF: ia,
            GG: "Guernsey",
            GH: r,
            GI: "Gibraltar",
            GL: ja,
            GM: "Gambia",
            GN: "Guin\u00e9e",
            GP: "Guadeloupe",
            GQ: ha,
            GR: "\u0395\u03bb\u03bb\u03ac\u03b4\u03b1",
            GS: "South Georgia and the South Sandwich Islands",
            GT: "Guatemala",
            GU: "Guam",
            GW: "Guin\u00e9 Bissau",
            GY: "Guyana",
            HK: "\u9999\u6e2f",
            HM: "Heard Island and McDonald Islands",
            HN: t,
            HR: "Hrvatska",
            HT: "Ha\u00efti",
            HU: "Magyarorsz\u00e1g",
            ID: u,
            IE: "Ireland",
            IL: "\u05d9\u05e9\u05e8\u05d0\u05dc",
            IM: "Isle of Man",
            IN: W,
            IO: "British Indian Ocean Territory",
            IQ: "\u0627\u0644\u0639\u0631\u0627\u0642",
            IR: "\u0627\u06cc\u0631\u0627\u0646",
            IS: "\u00cdsland",
            IT: "Italia",
            JE: "Jersey",
            JM: "Jamaica",
            JO: "\u0627\u0644\u0623\u0631\u062f\u0646",
            JP: "\u65e5\u672c",
            KE: "Kenya",
            KG: eb,
            KH: "\u1780\u1798\u17d2\u1796\u17bb\u1787\u17b6",
            KI: ka,
            KM: lb,
            KN: Da,
            KP: mb,
            KR: "\ub300\ud55c\ubbfc\uad6d",
            KW: "\u0627\u0644\u0643\u0648\u064a\u062a",
            KY: ea,
            KZ: "\u041a\u0430\u0437\u0430\u0445\u0441\u0442\u0430\u043d",
            LA: "\u0e25\u0e32\u0e27",
            LB: "\u0644\u0628\u0646\u0627\u0646",
            LC: "Saint Lucia",
            LI: "Liechtenstein",
            LK: "\u0b87\u0bb2\u0b99\u0bcd\u0b95\u0bc8",
            LR: "Liberia",
            LS: "Lesotho",
            LT: "Lietuva",
            LU: ma,
            LV: "Latvija",
            LY: "\u0644\u064a\u0628\u064a\u0627",
            MA: "\u0627\u0644\u0645\u063a\u0631\u0628",
            MC: "Monaco",
            MD: pa,
            ME: "\u0426\u0440\u043d\u0430 \u0413\u043e\u0440\u0430",
            MG: na,
            MH: oa,
            MK: "\u041c\u0430\u043a\u0435\u0434\u043e\u043d\u0438\u0458\u0430",
            ML: "\u0645\u0627\u0644\u064a",
            MM: "Myanmar",
            MN: "\u8499\u53e4",
            MO: "\u6fb3\u95e8",
            MP: ta,
            MQ: "Martinique",
            MR: "\u0645\u0648\u0631\u064a\u062a\u0627\u0646\u064a\u0627",
            MS: "Montserrat",
            MT: "Malta",
            MU: "Mauritius",
            MV: "Maldives",
            MW: "Malawi",
            MX: "M\u00e9xico",
            MY: "Malaysia",
            MZ: "Mo\u00e7ambique",
            NA: "Namibia",
            NC: ua,
            NE: "Niger",
            NF: sa,
            NG: y,
            NI: "Nicaragua",
            NL: "Nederland",
            NO: "Norge",
            NP: "\u0928\u0947\u092a\u093e\u0932",
            NR: "Nauru",
            NT: "Neutral Zone",
            NU: "Niue",
            NZ: ra,
            OM: "\u0639\u0645\u0627\u0646",
            PA: "Panam\u00e1",
            PE: "Per\u00fa",
            PF: xa,
            PG: z,
            PH: wa,
            PK: V,
            PL: "Polska",
            PM: Fa,
            PN: "Pitcairn",
            PR: ya,
            PS: "\u0641\u0644\u0633\u0637\u064a\u0646",
            PT: "Portugal",
            PW: "Palau",
            PY: va,
            QA: "\u0642\u0637\u0631",
            QO: "Outlying Oceania",
            QU: "European Union",
            RE: "R\u00e9union",
            RO: "Rom\u00e2nia",
            RS: "\u0421\u0440\u0431\u0438\u0458\u0430",
            RU: "\u0420\u043e\u0441\u0441\u0438\u044f",
            RW: A,
            SA: jb,
            SB: Ja,
            SC: Ha,
            SD: "\u0627\u0644\u0633\u0648\u062f\u0627\u0646",
            SE: "Sverige",
            SG: "\u65b0\u52a0\u5761",
            SH: "Saint Helena",
            SI: "Slovenija",
            SJ: Ka,
            SK: Ia,
            SL: "Sierra Leone",
            SM: "San Marino",
            SN: C,
            SO: "Somali",
            SR: "Suriname",
            ST: Ma,
            SU: "Union of Soviet Socialist Republics",
            SV: "El Salvador",
            SY: "\u0633\u0648\u0631\u064a\u0627",
            SZ: La,
            TC: Pa,
            TD: "\u062a\u0634\u0627\u062f",
            TF: "French Southern Territories",
            TG: "Togo",
            TH: "\u0e1b\u0e23\u0e30\u0e40\u0e17\u0e28\u0e44\u0e17\u0e22",
            TJ: "\u062a\u0627\u062c\u06cc\u06a9\u0633\u062a\u0627\u0646",
            TK: D,
            TL: Oa,
            TM: "\u0422\u0443\u0440\u043a\u043c\u0435\u043d\u0438\u0441\u0442\u0430\u043d",
            TN: "\u062a\u0648\u0646\u0633",
            TO: "Tonga",
            TR: F,
            TT: "Trinidad y Tobago",
            TV: E,
            TW: "\u53f0\u6e7e",
            TZ: Na,
            UA: "\u0423\u043a\u0440\u0430\u0457\u043d\u0430",
            UG: "Uganda",
            UM: Ta,
            US: Sa,
            UY: "Uruguay",
            UZ: "\u040e\u0437\u0431\u0435\u043a\u0438\u0441\u0442\u043e\u043d",
            VA: "Vaticano",
            VC: Ea,
            VE: "Venezuela",
            VG: da,
            VI: Qa,
            VN: "Vi\u1ec7t Nam",
            VU: G,
            WF: Va,
            WS: "Samoa",
            YD: "People's Democratic Republic of Yemen",
            YE: "\u0627\u0644\u064a\u0645\u0646",
            YT: "Mayotte",
            ZA: B,
            ZM: "Zambia",
            ZW: "Zimbabwe",
            ZZ: Ua,
            aa_DJ: "Jabuuti",
            aa_ER: "\u00c9rythr\u00e9e",
            aa_ER_SAAHO: "\u00c9rythr\u00e9e",
            aa_ET: v,
            af_NA: "Namibi\u00eb",
            af_ZA: "Suid-Afrika",
            ak_GH: r,
            am_ET: X,
            ar_AE: hb,
            ar_BH: "\u0627\u0644\u0628\u062d\u0631\u064a\u0646",
            ar_DJ: "\u062c\u064a\u0628\u0648\u062a\u064a",
            ar_DZ: "\u0627\u0644\u062c\u0632\u0627\u0626\u0631",
            ar_EG: "\u0645\u0635\u0631",
            ar_EH: ib,
            ar_ER: "\u0627\u0631\u064a\u062a\u0631\u064a\u0627",
            ar_IL: "\u0627\u0633\u0631\u0627\u0626\u064a\u0644",
            ar_IQ: "\u0627\u0644\u0639\u0631\u0627\u0642",
            ar_JO: "\u0627\u0644\u0623\u0631\u062f\u0646",
            ar_KM: lb,
            ar_KW: "\u0627\u0644\u0643\u0648\u064a\u062a",
            ar_LB: "\u0644\u0628\u0646\u0627\u0646",
            ar_LY: "\u0644\u064a\u0628\u064a\u0627",
            ar_MA: "\u0627\u0644\u0645\u063a\u0631\u0628",
            ar_MR: "\u0645\u0648\u0631\u064a\u062a\u0627\u0646\u064a\u0627",
            ar_OM: "\u0639\u0645\u0627\u0646",
            ar_PS: "\u0641\u0644\u0633\u0637\u064a\u0646",
            ar_QA: "\u0642\u0637\u0631",
            ar_SA: jb,
            ar_SD: "\u0627\u0644\u0633\u0648\u062f\u0627\u0646",
            ar_SY: "\u0633\u0648\u0631\u064a\u0627",
            ar_TD: "\u062a\u0634\u0627\u062f",
            ar_TN: "\u062a\u0648\u0646\u0633",
            ar_YE: "\u0627\u0644\u064a\u0645\u0646",
            as_IN: "\u09ad\u09be\u09f0\u09a4",
            ay_BO: k,
            az_AZ: "Az\u0259rbaycan",
            az_Cyrl_AZ: "\u0410\u0437\u04d9\u0440\u0431\u0430\u0458\u04b9\u0430\u043d",
            az_Latn_AZ: "Azerbaycan",
            be_BY: db,
            bg_BG: "\u0411\u044a\u043b\u0433\u0430\u0440\u0438\u044f",
            bi_VU: G,
            bn_BD: "\u09ac\u09be\u0982\u09b2\u09be\u09a6\u09c7\u09b6",
            bn_IN: "\u09ad\u09be\u09b0\u09a4",
            bo_CN: "\u0f62\u0f92\u0fb1\u0f0b\u0f53\u0f42",
            bo_IN: "\u0f62\u0f92\u0fb1\u0f0b\u0f42\u0f62\u0f0b",
            bs_BA: p,
            byn_ER: "\u12a4\u122d\u1275\u122b",
            ca_AD: "Andorra",
            ca_ES: "Espanya",
            cch_NG: y,
            ch_GU: "Guam",
            chk_FM: x,
            cop_Arab_EG: "\u0645\u0635\u0631",
            cop_Arab_US: kb,
            cop_EG: "\u0645\u0635\u0631",
            cop_US: kb,
            cs_CZ: cb,
            cy_GB: "Prydain Fawr",
            da_DK: "Danmark",
            da_GL: "Gr\u00f8nland",
            de_AT: "\u00d6sterreich",
            de_BE: "Belgien",
            de_CH: "Schweiz",
            de_DE: "Deutschland",
            de_LI: "Liechtenstein",
            de_LU: "Luxemburg",
            dv_MV: "Maldives",
            dz_BT: "Bhutan",
            ee_GH: r,
            ee_TG: "Togo",
            efi_NG: y,
            el_CY: "\u039a\u03cd\u03c0\u03c1\u03bf\u03c2",
            el_GR: "\u0395\u03bb\u03bb\u03ac\u03b4\u03b1",
            en_AG: ca,
            en_AI: "Anguilla",
            en_AS: b,
            en_AU: "Australia",
            en_BB: "Barbados",
            en_BE: "Belgium",
            en_BM: "Bermuda",
            en_BS: "Bahamas",
            en_BW: q,
            en_BZ: "Belize",
            en_CA: "Canada",
            en_CC: "Cocos Islands",
            en_CK: "Cook Islands",
            en_CM: "Cameroon",
            en_CX: fa,
            en_DM: "Dominica",
            en_FJ: "Fiji",
            en_FK: ga,
            en_FM: x,
            en_GB: Ra,
            en_GD: "Grenada",
            en_GG: "Guernsey",
            en_GH: r,
            en_GI: "Gibraltar",
            en_GM: "Gambia",
            en_GU: "Guam",
            en_GY: "Guyana",
            en_HK: "Hong Kong",
            en_HN: t,
            en_IE: "Ireland",
            en_IM: "Isle of Man",
            en_IN: "India",
            en_JE: "Jersey",
            en_JM: "Jamaica",
            en_KE: "Kenya",
            en_KI: ka,
            en_KN: Da,
            en_KY: ea,
            en_LC: "Saint Lucia",
            en_LR: "Liberia",
            en_LS: "Lesotho",
            en_MH: oa,
            en_MP: ta,
            en_MS: "Montserrat",
            en_MT: "Malta",
            en_MU: "Mauritius",
            en_MW: "Malawi",
            en_NA: "Namibia",
            en_NF: sa,
            en_NG: y,
            en_NR: "Nauru",
            en_NU: "Niue",
            en_NZ: ra,
            en_PG: z,
            en_PH: wa,
            en_PK: "Pakistan",
            en_PN: "Pitcairn",
            en_PR: ya,
            en_RW: A,
            en_SB: Ja,
            en_SC: Ha,
            en_SG: "Singapore",
            en_SH: "Saint Helena",
            en_SL: "Sierra Leone",
            en_SZ: La,
            en_TC: Pa,
            en_TK: D,
            en_TO: "Tonga",
            en_TT: "Trinidad and Tobago",
            en_TV: E,
            en_TZ: Na,
            en_UG: "Uganda",
            en_UM: Ta,
            en_US: Sa,
            en_US_POSIX: Sa,
            en_VC: Ea,
            en_VG: da,
            en_VI: Qa,
            en_VU: G,
            en_WS: "Samoa",
            en_ZA: B,
            en_ZM: "Zambia",
            en_ZW: "Zimbabwe",
            es_AR: "Argentina",
            es_BO: k,
            es_CL: "Chile",
            es_CO: "Colombia",
            es_CR: "Costa Rica",
            es_CU: "Cuba",
            es_DO: za,
            es_EC: "Ecuador",
            es_ES: "Espa\u00f1a",
            es_GQ: "Guinea Ecuatorial",
            es_GT: "Guatemala",
            es_HN: t,
            es_MX: "M\u00e9xico",
            es_NI: "Nicaragua",
            es_PA: "Panam\u00e1",
            es_PE: "Per\u00fa",
            es_PH: "Filipinas",
            es_PR: ya,
            es_PY: va,
            es_SV: "El Salvador",
            es_US: "Estados Unidos",
            es_UY: "Uruguay",
            es_VE: "Venezuela",
            et_EE: "Eesti",
            eu_ES: "Espainia",
            fa_AF: T,
            fa_IR: "\u0627\u06cc\u0631\u0627\u0646",
            fi_FI: "Suomi",
            fil_PH: wa,
            fj_FJ: "Fiji",
            fo_FO: "F\u00f8royar",
            fr_BE: "Belgique",
            fr_BF: "Burkina Faso",
            fr_BI: "Burundi",
            fr_BJ: "B\u00e9nin",
            fr_CA: "Canada",
            fr_CD: Ca,
            fr_CF: Ba,
            fr_CG: "Congo",
            fr_CH: "Suisse",
            fr_CI: "C\u00f4te d\u2019Ivoire",
            fr_CM: "Cameroun",
            fr_DJ: "Djibouti",
            fr_DZ: "Alg\u00e9rie",
            fr_FR: "France",
            fr_GA: "Gabon",
            fr_GF: ia,
            fr_GN: "Guin\u00e9e",
            fr_GP: "Guadeloupe",
            fr_GQ: ha,
            fr_HT: "Ha\u00efti",
            fr_KM: "Comores",
            fr_LU: ma,
            fr_MA: "Maroc",
            fr_MC: "Monaco",
            fr_MG: na,
            fr_ML: "Mali",
            fr_MQ: "Martinique",
            fr_MU: "Maurice",
            fr_NC: ua,
            fr_NE: "Niger",
            fr_PF: xa,
            fr_PM: Fa,
            fr_RE: "R\u00e9union",
            fr_RW: A,
            fr_SC: Ha,
            fr_SN: C,
            fr_SY: "Syrie",
            fr_TD: "Tchad",
            fr_TG: "Togo",
            fr_TN: "Tunisie",
            fr_VU: G,
            fr_WF: Va,
            fr_YT: "Mayotte",
            fur_IT: "Italia",
            ga_IE: "\u00c9ire",
            gaa_GH: r,
            gez_ER: "\u12a4\u122d\u1275\u122b",
            gez_ET: X,
            gil_KI: ka,
            gl_ES: "Espa\u00f1a",
            gn_PY: va,
            gu_IN: "\u0aad\u0abe\u0ab0\u0aa4",
            gv_GB: Aa,
            ha_Arab_NG: "\u0646\u064a\u062c\u064a\u0631\u064a\u0627",
            ha_GH: "\u063a\u0627\u0646\u0627",
            ha_Latn_GH: r,
            ha_Latn_NE: "Niger",
            ha_Latn_NG: "Nig\u00e9ria",
            ha_NE: "\u0627\u0644\u0646\u064a\u062c\u0631",
            ha_NG: "\u0646\u064a\u062c\u064a\u0631\u064a\u0627",
            haw_US: "\u02bbAmelika Hui P\u016b \u02bbIa",
            he_IL: "\u05d9\u05e9\u05e8\u05d0\u05dc",
            hi_IN: W,
            ho_PG: z,
            hr_BA: p,
            hr_HR: "Hrvatska",
            ht_HT: "Ha\u00efti",
            hu_HU: "Magyarorsz\u00e1g",
            hy_AM: gb,
            hy_AM_REVISED: gb,
            id_ID: u,
            ig_NG: y,
            ii_CN: "\ua34f\ua1e9",
            is_IS: "\u00cdsland",
            it_CH: "Svizzera",
            it_IT: "Italia",
            it_SM: "San Marino",
            ja_JP: "\u65e5\u672c",
            ka_GE: "\u10e1\u10d0\u10e5\u10d0\u10e0\u10d7\u10d5\u10d4\u10da\u10dd",
            kaj_NG: y,
            kam_KE: "Kenya",
            kcg_NG: y,
            kfo_NG: "Nig\u00e9ria",
            kk_KZ: "\u049a\u0430\u0437\u0430\u049b\u0441\u0442\u0430\u043d",
            kl_GL: ja,
            km_KH: "\u1780\u1798\u17d2\u1796\u17bb\u1787\u17b6",
            kn_IN: "\u0cad\u0cbe\u0cb0\u0ca4",
            ko_KP: mb,
            ko_KR: "\ub300\ud55c\ubbfc\uad6d",
            kok_IN: W,
            kos_FM: x,
            kpe_GN: "Guin\u00e9e",
            kpe_LR: "Lib\u00e9ria",
            ks_IN: W,
            ku_IQ: "Irak",
            ku_IR: "\u0130ran",
            ku_Latn_IQ: "Irak",
            ku_Latn_IR: "\u0130ran",
            ku_Latn_SY: "Suriye",
            ku_Latn_TR: F,
            ku_SY: "Suriye",
            ku_TR: F,
            kw_GB: Aa,
            ky_Cyrl_KG: eb,
            ky_KG: "K\u0131rg\u0131zistan",
            la_VA: "Vaticano",
            lb_LU: ma,
            ln_CD: Ca,
            ln_CG: "Kongo",
            lo_LA: "Laos",
            lt_LT: "Lietuva",
            lv_LV: "Latvija",
            mg_MG: na,
            mh_MH: oa,
            mi_NZ: ra,
            mk_MK: "\u041c\u0430\u043a\u0435\u0434\u043e\u043d\u0438\u0458\u0430",
            ml_IN: "\u0d07\u0d28\u0d4d\u0d24\u0d4d\u0d2f",
            mn_Cyrl_MN: "\u041c\u043e\u043d\u0433\u043e\u043b\u0438\u044f",
            mn_MN: "\u041c\u043e\u043d\u0433\u043e\u043b\u0438\u044f",
            mr_IN: W,
            ms_BN: "Brunei",
            ms_MY: "Malaysia",
            ms_SG: "Singapura",
            mt_MT: "Malta",
            my_MM: "Myanmar",
            na_NR: "Nauru",
            nb_NO: "Norge",
            nb_SJ: Ka,
            ne_NP: "\u0928\u0947\u092a\u093e\u0932",
            niu_NU: "Niue",
            nl_AN: qa,
            nl_AW: "Aruba",
            nl_BE: "Belgi\u00eb",
            nl_NL: "Nederland",
            nl_SR: "Suriname",
            nn_NO: "Noreg",
            nr_ZA: B,
            nso_ZA: B,
            ny_MW: "Malawi",
            om_ET: v,
            om_KE: "Keeniyaa",
            or_IN: "\u0b2d\u0b3e\u0b30\u0b24",
            pa_Arab_PK: V,
            pa_Guru_IN: "\u0a2d\u0a3e\u0a30\u0a24",
            pa_IN: "\u0a2d\u0a3e\u0a30\u0a24",
            pa_PK: V,
            pap_AN: qa,
            pau_PW: "Palau",
            pl_PL: "Polska",
            pon_FM: x,
            ps_AF: T,
            pt_AO: "Angola",
            pt_BR: "Brasil",
            pt_CV: "Cabo Verde",
            pt_GW: "Guin\u00e9 Bissau",
            pt_MZ: "Mo\u00e7ambique",
            pt_PT: "Portugal",
            pt_ST: Ma,
            pt_TL: Oa,
            qu_BO: k,
            qu_PE: "Per\u00fa",
            rm_CH: "Schweiz",
            rn_BI: "Burundi",
            ro_MD: pa,
            ro_RO: "Rom\u00e2nia",
            ru_BY: db,
            ru_KG: eb,
            ru_KZ: "\u041a\u0430\u0437\u0430\u0445\u0441\u0442\u0430\u043d",
            ru_RU: "\u0420\u043e\u0441\u0441\u0438\u044f",
            ru_UA: "\u0423\u043a\u0440\u0430\u0438\u043d\u0430",
            rw_RW: A,
            sa_IN: W,
            sd_Deva_IN: W,
            sd_IN: W,
            se_FI: "Finland",
            se_NO: "Norge",
            sg_CF: Ba,
            sh_BA: "Bosnia and Herzegovina",
            sh_CS: Ga,
            si_LK: "Sri Lanka",
            sid_ET: v,
            sk_SK: Ia,
            sl_SI: "Slovenija",
            sm_AS: b,
            sm_WS: "Samoa",
            so_DJ: "Jabuuti",
            so_ET: "Itoobiya",
            so_KE: "Kiiniya",
            so_SO: "Soomaaliya",
            sq_AL: "Shqip\u00ebria",
            sr_BA: "\u0411\u043e\u0441\u043d\u0430 \u0438 \u0425\u0435\u0440\u0446\u0435\u0433\u043e\u0432\u0438\u043d\u0430",
            sr_CS: "\u0421\u0440\u0431\u0438\u0458\u0430 \u0438 \u0426\u0440\u043d\u0430 \u0413\u043e\u0440\u0430",
            sr_Cyrl_BA: "\u0411\u043e\u0441\u043d\u0438\u044f",
            sr_Cyrl_CS: "\u0421\u0435\u0440\u0431\u0438\u044f \u0438 \u0427\u0435\u0440\u043d\u043e\u0433\u043e\u0440\u0438\u044f",
            sr_Cyrl_ME: "\u0427\u0435\u0440\u043d\u043e\u0433\u043e\u0440\u0438\u044f",
            sr_Cyrl_RS: "\u0421\u0435\u0440\u0431\u0438\u044f",
            sr_Latn_BA: p,
            sr_Latn_CS: "Srbija i Crna Gora",
            sr_Latn_ME: "Crna Gora",
            sr_Latn_RS: "Srbija",
            sr_ME: "\u0426\u0440\u043d\u0430 \u0413\u043e\u0440\u0430",
            sr_RS: "\u0421\u0440\u0431\u0438\u0458\u0430",
            ss_SZ: La,
            ss_ZA: B,
            st_LS: "Lesotho",
            st_ZA: B,
            su_ID: u,
            sv_AX: "\u00c5land",
            sv_FI: "Finland",
            sv_SE: "Sverige",
            sw_KE: "Kenya",
            sw_TZ: Na,
            sw_UG: "Uganda",
            swb_KM: lb,
            syr_SY: "Syria",
            ta_IN: "\u0b87\u0ba8\u0bcd\u0ba4\u0bbf\u0baf\u0bbe",
            ta_LK: "\u0b87\u0bb2\u0b99\u0bcd\u0b95\u0bc8",
            ta_SG: "\u0b9a\u0bbf\u0b99\u0bcd\u0b95\u0baa\u0bcd\u0baa\u0bc2\u0bb0\u0bcd",
            te_IN: "\u0c2d\u0c3e\u0c30\u0c24 \u0c26\u0c47\u0c33\u0c02",
            tet_TL: Oa,
            tg_Cyrl_TJ: "\u0422\u0430\u0434\u0436\u0438\u043a\u0438\u0441\u0442\u0430\u043d",
            tg_TJ: "\u062a\u0627\u062c\u06a9\u0633\u062a\u0627\u0646",
            th_TH: "\u0e1b\u0e23\u0e30\u0e40\u0e17\u0e28\u0e44\u0e17\u0e22",
            ti_ER: "\u12a4\u122d\u1275\u122b",
            ti_ET: X,
            tig_ER: "\u12a4\u122d\u1275\u122b",
            tk_TM: "\u062a\u0631\u06a9\u0645\u0646\u0633\u062a\u0627\u0646",
            tkl_TK: D,
            tn_BW: q,
            tn_ZA: B,
            to_TO: "Tonga",
            tpi_PG: z,
            tr_CY: "G\u00fcney K\u0131br\u0131s Rum Kesimi",
            tr_TR: F,
            ts_ZA: B,
            tt_RU: "\u0420\u043e\u0441\u0441\u0438\u044f",
            tvl_TV: E,
            ty_PF: xa,
            uk_UA: "\u0423\u043a\u0440\u0430\u0457\u043d\u0430",
            uli_FM: x,
            und_ZZ: Ua,
            ur_IN: "\u0628\u06be\u0627\u0631\u062a",
            ur_PK: V,
            uz_AF: "Afganistan",
            uz_Arab_AF: T,
            uz_Cyrl_UZ: "\u0423\u0437\u0431\u0435\u043a\u0438\u0441\u0442\u0430\u043d",
            uz_Latn_UZ: "O\u02bfzbekiston",
            uz_UZ: "\u040e\u0437\u0431\u0435\u043a\u0438\u0441\u0442\u043e\u043d",
            ve_ZA: B,
            vi_VN: "Vi\u1ec7t Nam",
            wal_ET: X,
            wo_Arab_SN: "\u0627\u0644\u0633\u0646\u063a\u0627\u0644",
            wo_Latn_SN: C,
            wo_SN: C,
            xh_ZA: B,
            yap_FM: x,
            yo_NG: y,
            zh_CN: "\u4e2d\u56fd",
            zh_HK: "\u9999\u6e2f",
            zh_Hans_CN: "\u4e2d\u56fd",
            zh_Hans_SG: "\u65b0\u52a0\u5761",
            zh_Hant_HK: "\u4e2d\u83ef\u4eba\u6c11\u5171\u548c\u570b\u9999\u6e2f\u7279\u5225\u884c\u653f\u5340",
            zh_Hant_MO: "\u6fb3\u9580",
            zh_Hant_TW: "\u81fa\u7063",
            zh_MO: "\u6fb3\u95e8",
            zh_SG: "\u65b0\u52a0\u5761",
            zh_TW: "\u53f0\u6e7e",
            zu_ZA: B
        }, LANGUAGE: {
            aa: "afar",
            ab: "\u0430\u0431\u0445\u0430\u0437\u0441\u043a\u0438\u0439",
            ace: "Aceh",
            ach: "Acoli",
            ada: "Adangme",
            ady: "\u0430\u0434\u044b\u0433\u0435\u0439\u0441\u043a\u0438\u0439",
            ae: "Avestan",
            af: "Afrikaans",
            afa: "Afro-Asiatic Language",
            afh: "Afrihili",
            ain: "Ainu",
            ak: "Akan",
            akk: "Akkadian",
            ale: "Aleut",
            alg: "Algonquian Language",
            alt: "Southern Altai",
            am: "\u12a0\u121b\u122d\u129b",
            an: "Aragonese",
            ang: "Old English",
            anp: "Angika",
            apa: "Apache Language",
            ar: "\u0627\u0644\u0639\u0631\u0628\u064a\u0629",
            arc: "Aramaic",
            arn: "Araucanian",
            arp: "Arapaho",
            art: "Artificial Language",
            arw: "Arawak",
            as: "\u0985\u09b8\u09ae\u09c0\u09af\u09bc\u09be",
            ast: "asturiano",
            ath: "Athapascan Language",
            aus: "Australian Language",
            av: "\u0430\u0432\u0430\u0440\u0441\u043a\u0438\u0439",
            awa: "Awadhi",
            ay: "aimara",
            az: "az\u0259rbaycanca",
            az_Arab: "\u062a\u0631\u06a9\u06cc \u0622\u0630\u0631\u0628\u0627\u06cc\u062c\u0627\u0646\u06cc",
            az_Cyrl: "\u0410\u0437\u04d9\u0440\u0431\u0430\u0458\u04b9\u0430\u043d",
            az_Latn: "Azerice",
            ba: "\u0431\u0430\u0448\u043a\u0438\u0440\u0441\u043a\u0438\u0439",
            bad: "Banda",
            bai: "Bamileke Language",
            bal: "\u0628\u0644\u0648\u0686\u06cc",
            ban: "Balin",
            bas: "Basa",
            bat: "Baltic Language",
            be: "\u0431\u0435\u043b\u0430\u0440\u0443\u0441\u043a\u0430\u044f",
            bej: "Beja",
            bem: "Bemba",
            ber: "Berber",
            bg: "\u0431\u044a\u043b\u0433\u0430\u0440\u0441\u043a\u0438",
            bh: "\u092c\u093f\u0939\u093e\u0930\u0940",
            bho: "Bhojpuri",
            bi: "bichelamar ; bislama",
            bik: "Bikol",
            bin: "Bini",
            bla: "Siksika",
            bm: "bambara",
            bn: "\u09ac\u09be\u0982\u09b2\u09be",
            bnt: "Bantu",
            bo: "\u0f54\u0f7c\u0f51\u0f0b\u0f66\u0f90\u0f51\u0f0b",
            br: "breton",
            bra: "Braj",
            bs: "Bosanski",
            btk: "Batak",
            bua: "Buriat",
            bug: "Bugis",
            byn: "\u1265\u120a\u1295",
            ca: "catal\u00e0",
            cad: "Caddo",
            cai: "Central American Indian Language",
            car: "Carib",
            cau: "Caucasian Language",
            cch: "Atsam",
            ce: "\u0447\u0435\u0447\u0435\u043d\u0441\u043a\u0438\u0439",
            ceb: "Cebuano",
            cel: "Celtic Language",
            ch: "Chamorro",
            chb: "Chibcha",
            chg: "Chagatai",
            chk: "Chuukese",
            chm: "\u043c\u0430\u0440\u0438\u0439\u0441\u043a\u0438\u0439 (\u0447\u0435\u0440\u0435\u043c\u0438\u0441\u0441\u043a\u0438\u0439)",
            chn: "Chinook Jargon",
            cho: "Choctaw",
            chp: "Chipewyan",
            chr: "Cherokee",
            chy: "Cheyenne",
            cmc: "Chamic Language",
            co: "corse",
            cop: "\u0642\u0628\u0637\u064a\u0629",
            cop_Arab: "\u0642\u0628\u0637\u064a\u0629",
            cpe: "English-based Creole or Pidgin",
            cpf: "French-based Creole or Pidgin",
            cpp: "Portuguese-based Creole or Pidgin",
            cr: "Cree",
            crh: "Crimean Turkish",
            crp: "Creole or Pidgin",
            cs: "\u010de\u0161tina",
            csb: "Kashubian",
            cu: "Church Slavic",
            cus: "Cushitic Language",
            cv: "\u0447\u0443\u0432\u0430\u0448\u0441\u043a\u0438\u0439",
            cy: "Cymraeg",
            da: "dansk",
            dak: "Dakota",
            dar: "\u0434\u0430\u0440\u0433\u0432\u0430",
            day: "Dayak",
            de: "Deutsch",
            del: "Delaware",
            den: "Slave",
            dgr: "Dogrib",
            din: "Dinka",
            doi: "\u0627\u0644\u062f\u0648\u062c\u0631\u0649",
            dra: "Dravidian Language",
            dsb: "Lower Sorbian",
            dua: "Duala",
            dum: "Middle Dutch",
            dv: "Divehi",
            dyu: "dioula",
            dz: "\u0f62\u0fab\u0f7c\u0f44\u0f0b\u0f41",
            ee: "Ewe",
            efi: "Efik",
            egy: "Ancient Egyptian",
            eka: "Ekajuk",
            el: "\u0395\u03bb\u03bb\u03b7\u03bd\u03b9\u03ba\u03ac",
            elx: "Elamite",
            en: "English",
            enm: "Middle English",
            eo: "esperanto",
            es: "espa\u00f1ol",
            et: "eesti",
            eu: "euskara",
            ewo: "Ewondo",
            fa: "\u0641\u0627\u0631\u0633\u06cc",
            fan: "fang",
            fat: "Fanti",
            ff: "Fulah",
            fi: "suomi",
            fil: "Filipino",
            fiu: "Finno-Ugrian Language",
            fj: "Fijian",
            fo: "f\u00f8royskt",
            fon: "Fon",
            fr: "fran\u00e7ais",
            frm: "Middle French",
            fro: "Old French",
            frr: "Northern Frisian",
            frs: "Eastern Frisian",
            fur: "friulano",
            fy: "Fries",
            ga: "Gaeilge",
            gaa: "Ga",
            gay: "Gayo",
            gba: "Gbaya",
            gd: "Scottish Gaelic",
            gem: "Germanic Language",
            gez: "\u130d\u12d5\u12dd\u129b",
            gil: "Gilbertese",
            gl: "galego",
            gmh: "Middle High German",
            gn: "guaran\u00ed",
            goh: "Old High German",
            gon: "Gondi",
            gor: "Gorontalo",
            got: "Gothic",
            grb: "Grebo",
            grc: "\u0391\u03c1\u03c7\u03b1\u03af\u03b1 \u0395\u03bb\u03bb\u03b7\u03bd\u03b9\u03ba\u03ac",
            gsw: "Schweizerdeutsch",
            gu: "\u0a97\u0ac1\u0a9c\u0ab0\u0abe\u0aa4\u0ac0",
            gv: "Gaelg",
            gwi: "Gwich\u02bcin",
            ha: "\u0627\u0644\u0647\u0648\u0633\u0627",
            ha_Arab: "\u0627\u0644\u0647\u0648\u0633\u0627",
            ha_Latn: "haoussa",
            hai: "Haida",
            haw: "\u02bb\u014dlelo Hawai\u02bbi",
            he: "\u05e2\u05d1\u05e8\u05d9\u05ea",
            hi: "\u0939\u093f\u0902\u0926\u0940",
            hil: "Hiligaynon",
            him: "Himachali",
            hit: "Hittite",
            hmn: "Hmong",
            ho: "Hiri Motu",
            hr: "hrvatski",
            hsb: "Upper Sorbian",
            ht: "ha\u00eftien",
            hu: "magyar",
            hup: "Hupa",
            hy: "\u0540\u0561\u0575\u0565\u0580\u0567\u0576",
            hz: "Herero",
            ia: "interlingvao",
            iba: "Iban",
            id: "Bahasa Indonesia",
            ie: "Interlingue",
            ig: "Igbo",
            ii: "\ua188\ua320\ua259",
            ijo: "Ijo",
            ik: "Inupiaq",
            ilo: "Iloko",
            inc: "Indic Language",
            ine: "Indo-European Language",
            inh: "\u0438\u043d\u0433\u0443\u0448\u0441\u043a\u0438\u0439",
            io: "Ido",
            ira: "Iranian Language",
            iro: "Iroquoian Language",
            is: "\u00edslenska",
            it: "italiano",
            iu: "Inuktitut",
            ja: "\u65e5\u672c\u8a9e",
            jbo: "Lojban",
            jpr: "Judeo-Persian",
            jrb: "Judeo-Arabic",
            jv: "Jawa",
            ka: "\u10e5\u10d0\u10e0\u10d7\u10e3\u10da\u10d8",
            kaa: "\u043a\u0430\u0440\u0430\u043a\u0430\u043b\u043f\u0430\u043a\u0441\u043a\u0438\u0439",
            kab: "kabyle",
            kac: "Kachin",
            kaj: "Jju",
            kam: "Kamba",
            kar: "Karen",
            kaw: "Kawi",
            kbd: "\u043a\u0430\u0431\u0430\u0440\u0434\u0438\u043d\u0441\u043a\u0438\u0439",
            kcg: "Tyap",
            kfo: "koro",
            kg: "Kongo",
            kha: "Khasi",
            khi: "Khoisan Language",
            kho: "Khotanese",
            ki: "Kikuyu",
            kj: "Kuanyama",
            kk: "\u049a\u0430\u0437\u0430\u049b",
            kl: "kalaallisut",
            km: "\u1797\u17b6\u179f\u17b6\u1781\u17d2\u1798\u17c2\u179a",
            kmb: "quimbundo",
            kn: "\u0c95\u0ca8\u0ccd\u0ca8\u0ca1",
            ko: "\ud55c\uad6d\uc5b4",
            kok: "\u0915\u094b\u0902\u0915\u0923\u0940",
            kos: "Kosraean",
            kpe: "kpell\u00e9",
            kr: "Kanuri",
            krc: "\u043a\u0430\u0440\u0430\u0447\u0430\u0435\u0432\u043e-\u0431\u0430\u043b\u043a\u0430\u0440\u0441\u043a\u0438\u0439",
            krl: "\u043a\u0430\u0440\u0435\u043b\u044c\u0441\u043a\u0438\u0439",
            kro: "Kru",
            kru: "Kurukh",
            ks: "\u0915\u093e\u0936\u094d\u092e\u093f\u0930\u0940",
            ku: "K\u00fcrt\u00e7e",
            ku_Arab: "\u0627\u0644\u0643\u0631\u062f\u064a\u0629",
            ku_Latn: "K\u00fcrt\u00e7e",
            kum: "\u043a\u0443\u043c\u044b\u043a\u0441\u043a\u0438\u0439",
            kut: "Kutenai",
            kv: "Komi",
            kw: "kernewek",
            ky: "K\u0131rg\u0131zca",
            ky_Arab: "\u0627\u0644\u0642\u064a\u0631\u063a\u0633\u062a\u0627\u0646\u064a\u0629",
            ky_Cyrl: "\u043a\u0438\u0440\u0433\u0438\u0437\u0441\u043a\u0438\u0439",
            la: "latino",
            lad: "\u05dc\u05d3\u05d9\u05e0\u05d5",
            lah: "\u0644\u0627\u0647\u0646\u062f\u0627",
            lam: "Lamba",
            lb: "luxembourgeois",
            lez: "\u043b\u0435\u0437\u0433\u0438\u043d\u0441\u043a\u0438\u0439",
            lg: "Ganda",
            li: "Limburgs",
            ln: "lingala",
            lo: "Lao",
            lol: "mongo",
            loz: "Lozi",
            lt: "lietuvi\u0173",
            lu: "luba-katanga",
            lua: "luba-lulua",
            lui: "Luiseno",
            lun: "Lunda",
            luo: "Luo",
            lus: "Lushai",
            lv: "latvie\u0161u",
            mad: "Madura",
            mag: "Magahi",
            mai: "Maithili",
            mak: "Makassar",
            man: "Mandingo",
            map: "Austronesian",
            mas: "Masai",
            mdf: "\u043c\u043e\u043a\u0448\u0430",
            mdr: "Mandar",
            men: "Mende",
            mg: "malgache",
            mga: "Middle Irish",
            mh: "Marshallese",
            mi: "Maori",
            mic: "Micmac",
            min: "Minangkabau",
            mis: "Miscellaneous Language",
            mk: "\u043c\u0430\u043a\u0435\u0434\u043e\u043d\u0441\u043a\u0438",
            mkh: "Mon-Khmer Language",
            ml: "\u0d2e\u0d32\u0d2f\u0d3e\u0d33\u0d02",
            mn: fb,
            mn_Cyrl: fb,
            mn_Mong: fb,
            mnc: "Manchu",
            mni: "Manipuri",
            mno: "Manobo Language",
            mo: "Moldavian",
            moh: "Mohawk",
            mos: "mor\u00e9 ; mossi",
            mr: "\u092e\u0930\u093e\u0920\u0940",
            ms: "Bahasa Melayu",
            mt: "Malti",
            mul: "Multiple Languages",
            mun: "Munda Language",
            mus: "Creek",
            mwl: "Mirandese",
            mwr: "Marwari",
            my: "Burmese",
            myn: "Mayan Language",
            myv: "\u044d\u0440\u0437\u044f",
            na: "Nauru",
            nah: "Nahuatl",
            nai: "North American Indian Language",
            nap: "napoletano",
            nb: "norsk bokm\u00e5l",
            nd: "North Ndebele",
            nds: "Low German",
            ne: "\u0928\u0947\u092a\u093e\u0932\u0940",
            "new": "Newari",
            ng: "Ndonga",
            nia: "Nias",
            nic: "Niger-Kordofanian Language",
            niu: "Niuean",
            nl: "Nederlands",
            nn: "nynorsk",
            no: "Norwegian",
            nog: "\u043d\u043e\u0433\u0430\u0439\u0441\u043a\u0438\u0439",
            non: "Old Norse",
            nqo: "N\u2019Ko",
            nr: "South Ndebele",
            nso: "Northern Sotho",
            nub: "Nubian Language",
            nv: "Navajo",
            nwc: "Classical Newari",
            ny: "nianja; chicheua; cheua",
            nym: "Nyamwezi",
            nyn: "Nyankole",
            nyo: "Nyoro",
            nzi: "Nzima",
            oc: "occitan",
            oj: "Ojibwa",
            om: "Oromoo",
            or: "\u0b13\u0b21\u0b3c\u0b3f\u0b06",
            os: "\u043e\u0441\u0435\u0442\u0438\u043d\u0441\u043a\u0438\u0439",
            osa: "Osage",
            ota: "Ottoman Turkish",
            oto: "Otomian Language",
            pa: "\u0a2a\u0a70\u0a1c\u0a3e\u0a2c\u0a40",
            pa_Arab: "\u067e\u0646\u062c\u0627\u0628",
            pa_Guru: "\u0a2a\u0a70\u0a1c\u0a3e\u0a2c\u0a40",
            paa: "Papuan Language",
            pag: "Pangasinan",
            pal: "Pahlavi",
            pam: "Pampanga",
            pap: "Papiamento",
            pau: "Palauan",
            peo: "Old Persian",
            phi: "Philippine Language",
            phn: "Phoenician",
            pi: "\u0e1a\u0e32\u0e25\u0e35",
            pl: "polski",
            pon: "Pohnpeian",
            pra: "Prakrit Language",
            pro: "Old Proven\u00e7al",
            ps: "\u067e\u069a\u062a\u0648",
            pt: "portugu\u00eas",
            qu: "quechua",
            raj: "Rajasthani",
            rap: "Rapanui",
            rar: "Rarotongan",
            rm: "R\u00e4toromanisch",
            rn: "roundi",
            ro: "rom\u00e2n\u0103",
            roa: "Romance Language",
            rom: "Romany",
            ru: "\u0440\u0443\u0441\u0441\u043a\u0438\u0439",
            rup: "Aromanian",
            rw: "rwanda",
            sa: "\u0938\u0902\u0938\u094d\u0915\u0943\u0924 \u092d\u093e\u0937\u093e",
            sad: "Sandawe",
            sah: "\u044f\u043a\u0443\u0442\u0441\u043a\u0438\u0439",
            sai: "South American Indian Language",
            sal: "Salishan Language",
            sam: "\u05d0\u05e8\u05de\u05d9\u05ea \u05e9\u05d5\u05de\u05e8\u05d5\u05e0\u05d9\u05ea",
            sas: "Sasak",
            sat: "Santali",
            sc: "Sardinian",
            scn: "siciliano",
            sco: "Scots",
            sd: "\u0938\u093f\u0928\u094d\u0927\u0940",
            sd_Arab: "\u0633\u0646\u062f\u06cc",
            sd_Deva: "\u0938\u093f\u0928\u094d\u0927\u0940",
            se: "nordsamiska",
            sel: "\u0441\u0435\u043b\u044c\u043a\u0443\u043f\u0441\u043a\u0438\u0439",
            sem: "Semitic Language",
            sg: "sangho",
            sga: "Old Irish",
            sgn: "Sign Language",
            sh: "Serbo-Croatian",
            shn: "Shan",
            si: "Sinhalese",
            sid: "Sidamo",
            sio: "Siouan Language",
            sit: "Sino-Tibetan Language",
            sk: "slovensk\u00fd",
            sl: "sloven\u0161\u010dina",
            sla: "Slavic Language",
            sm: "Samoan",
            sma: "sydsamiska",
            smi: "Sami Language",
            smj: "lulesamiska",
            smn: "Inari Sami",
            sms: "Skolt Sami",
            sn: "Shona",
            snk: "sonink\u00e9",
            so: "Soomaali",
            sog: "Sogdien",
            son: "Songhai",
            sq: "shqipe",
            sr: "\u0421\u0440\u043f\u0441\u043a\u0438",
            sr_Cyrl: "\u0441\u0435\u0440\u0431\u0441\u043a\u0438\u0439",
            sr_Latn: "Srpski",
            srn: "Sranantongo",
            srr: "s\u00e9r\u00e8re",
            ss: "Swati",
            ssa: "Nilo-Saharan Language",
            st: "Sesotho",
            su: "Sundan",
            suk: "Sukuma",
            sus: "soussou",
            sux: "Sumerian",
            sv: "svenska",
            sw: "Kiswahili",
            syc: "Classical Syriac",
            syr: "Syriac",
            ta: "\u0ba4\u0bae\u0bbf\u0bb4\u0bcd",
            tai: "Tai Language",
            te: "\u0c24\u0c46\u0c32\u0c41\u0c17\u0c41",
            tem: "Timne",
            ter: "Tereno",
            tet: "t\u00e9tum",
            tg: "\u062a\u0627\u062c\u06a9",
            tg_Arab: "\u062a\u0627\u062c\u06a9",
            tg_Cyrl: "\u0442\u0430\u0434\u0436\u0438\u043a\u0441\u043a\u0438\u0439",
            th: "\u0e44\u0e17\u0e22",
            ti: "\u1275\u130d\u122d\u129b",
            tig: "\u1275\u130d\u1228",
            tiv: "Tiv",
            tk: "\u062a\u0631\u06a9\u0645\u0646\u06cc",
            tkl: D,
            tl: "Tagalog",
            tlh: "Klingon",
            tli: "Tlingit",
            tmh: "tamacheq",
            tn: "Tswana",
            to: "Tonga",
            tog: "Nyasa Tonga",
            tpi: "Tok Pisin",
            tr: "T\u00fcrk\u00e7e",
            ts: "Tsonga",
            tsi: "Tsimshian",
            tt: "\u0442\u0430\u0442\u0430\u0440\u0441\u043a\u0438\u0439",
            tum: "Tumbuka",
            tup: "Tupi Language",
            tut: "\u0430\u043b\u0442\u0430\u0439\u0441\u043a\u0438\u0435 (\u0434\u0440\u0443\u0433\u0438\u0435)",
            tvl: E,
            tw: "Twi",
            ty: "tahitien",
            tyv: "\u0442\u0443\u0432\u0438\u043d\u0441\u043a\u0438\u0439",
            udm: "\u0443\u0434\u043c\u0443\u0440\u0442\u0441\u043a\u0438\u0439",
            ug: "\u0443\u0439\u0433\u0443\u0440\u0441\u043a\u0438\u0439",
            uga: "Ugaritic",
            uk: "\u0443\u043a\u0440\u0430\u0457\u043d\u0441\u044c\u043a\u0430",
            umb: "umbundu",
            und: "English",
            ur: "\u0627\u0631\u062f\u0648",
            uz: "\u040e\u0437\u0431\u0435\u043a",
            uz_Arab: "\u0627\u06c9\u0632\u0628\u06d0\u06a9",
            uz_Cyrl: "\u0443\u0437\u0431\u0435\u043a\u0441\u043a\u0438\u0439",
            uz_Latn: "o'zbekcha",
            vai: "Vai",
            ve: "Venda",
            vi: "Ti\u1ebfng Vi\u1ec7t",
            vo: "volapuko",
            vot: "Votic",
            wa: "Wallonisch",
            wak: "Wakashan Language",
            wal: "Walamo",
            war: "Waray",
            was: "Washo",
            wen: "Sorbian Language",
            wo: "wolof",
            wo_Arab: "\u0627\u0644\u0648\u0644\u0648\u0641",
            wo_Latn: "wolof",
            xal: "\u043a\u0430\u043b\u043c\u044b\u0446\u043a\u0438\u0439",
            xh: "Xhosa",
            yao: "iao",
            yap: "Yapese",
            yi: "\u05d9\u05d9\u05d3\u05d9\u05e9",
            yo: "Yoruba",
            ypk: "Yupik Language",
            za: "Zhuang",
            zap: "Zapotec",
            zen: "Zenaga",
            zh: "\u4e2d\u6587",
            zh_Hans: "\u4e2d\u6587",
            zh_Hant: "\u4e2d\u6587",
            znd: "Zande",
            zu: "Zulu",
            zun: "Zuni",
            zxx: "No linguistic content",
            zza: "Zaza"
        }
    };
    Z.locale.fg = function (a) {
        a = a.replace(/-/g, "_");
        Z.locale.P = a
    };
    Z.locale.Fa = function () {
        Z.locale.P || (Z.locale.P = "en");
        return Z.locale.P
    };
    Z.locale.I = {
        Dd: "DateTimeConstants",
        Md: "NumberFormatConstants",
        kc: "TimeZoneConstants",
        $b: la,
        lc: "TimeZoneSelectedIds",
        nc: "TimeZoneSelectedShortNames",
        mc: "TimeZoneSelectedLongNames",
        jc: "TimeZoneAllLongNames"
    };
    Z.locale.Ea = function (a) {
        return (a = a.match(/^\w{2,3}([-_]|$)/)) ? a[0].replace(/[_-]/g, "") : ""
    };
    Z.locale.pb = function (a) {
        return (a = a.match(/[-_]([a-zA-Z]{2}|\d{3})([-_]|$)/)) ? a[0].replace(/[_-]/g, "") : ""
    };
    Z.locale.Ye = function (a) {
        a = a.split(/[-_]/g);
        return 1 < a.length && a[1].match(/^[a-zA-Z]{4}$/) ? a[1] : ""
    };
    Z.locale.Ze = function (a) {
        return (a = a.match(/[-_]([a-z]{2,})/)) ? a[1] : ""
    };
    Z.locale.Ue = function (a) {
        var c = Z.locale.Ea(a) + "_" + Z.locale.pb(a);
        return c in Z.locale.K.COUNTRY ? Z.locale.K.COUNTRY[c] : a
    };
    Z.locale.Qe = function (a, c) {
        c || (c = Z.locale.qb());
        var d = Z.locale.pb(a);
        return d in c.COUNTRY ? c.COUNTRY[d] : a
    };
    Z.locale.Ve = function (a) {
        if (a in Z.locale.K.LANGUAGE)return Z.locale.K.LANGUAGE[a];
        var c = Z.locale.Ea(a);
        return c in Z.locale.K.LANGUAGE ? Z.locale.K.LANGUAGE[c] : a
    };
    Z.locale.Re = function (a, c) {
        c || (c = Z.locale.qb());
        if (a in c.LANGUAGE)return c.LANGUAGE[a];
        var d = Z.locale.Ea(a);
        return d in c.LANGUAGE ? c.LANGUAGE[d] : a
    };
    Z.locale.L = function (a, c, d) {
        Z.locale.m[c] || (Z.locale.m[c] = {});
        Z.locale.m[c][d] = a;
        Z.locale.P || (Z.locale.P = d)
    };
    Z.locale.tf = function (a, c) {
        return a in Z.locale.m && c in Z.locale.m[a]
    };
    Z.locale.m = {};
    Z.locale.Rf = function (a, c) {
        Z.locale.L(a, Z.locale.I.kc, c)
    };
    Z.locale.Pf = function (a, c) {
        Z.locale.L(a, Z.locale.I.$b, c)
    };
    Z.locale.Sf = function (a, c) {
        Z.locale.L(a, Z.locale.I.lc, c)
    };
    Z.locale.Uf = function (a, c) {
        Z.locale.L(a, Z.locale.I.nc, c)
    };
    Z.locale.Tf = function (a, c) {
        Z.locale.L(a, Z.locale.I.mc, c)
    };
    Z.locale.Qf = function (a, c) {
        Z.locale.L(a, Z.locale.I.jc, c)
    };
    Z.locale.qb = function () {
        var a = Z.locale.Fa(), a = a ? a : Z.locale.Fa();
        return la in Z.locale.m ? Z.locale.m.LocaleNameConstants[a] : void 0
    };
    Z.locale.Xe = function (a, c) {
        c = c ? c : Z.locale.Fa();
        if (a in Z.locale.m) {
            if (c in Z.locale.m[a])return Z.locale.m[a][c];
            c = c.split("_");
            return 1 < c.length && c[0] in Z.locale.m[a] ? Z.locale.m[a][c[0]] : Z.locale.m[a].en
        }
    };
    var google = {a: {}};
    google.a.b = {};
    google.a.b.languages = {
        af: !0,
        am: !0,
        az: !0,
        ar: !0,
        arb: "ar",
        bg: !0,
        bn: !0,
        ca: !0,
        cs: !0,
        cmn: "zh",
        da: !0,
        de: !0,
        el: !0,
        en: !0,
        en_gb: !0,
        es: !0,
        es_419: !0,
        et: !0,
        eu: !0,
        fa: !0,
        fi: !0,
        fil: !0,
        fr: !0,
        fr_ca: !0,
        gl: !0,
        ka: !0,
        gu: !0,
        he: "iw",
        hi: !0,
        hr: !0,
        hu: !0,
        hy: !0,
        id: !0,
        "in": "id",
        is: !0,
        it: !0,
        iw: !0,
        ja: !0,
        ji: "yi",
        jv: !1,
        jw: "jv",
        km: !0,
        kn: !0,
        ko: !0,
        lo: !0,
        lt: !0,
        lv: !0,
        ml: !0,
        mn: !0,
        mo: "ro",
        mr: !0,
        ms: !0,
        nb: "no",
        ne: !0,
        nl: !0,
        no: !0,
        pl: !0,
        pt: "pt_br",
        pt_br: !0,
        pt_pt: !0,
        ro: !0,
        ru: !0,
        si: !0,
        sk: !0,
        sl: !0,
        sr: !0,
        sv: !0,
        sw: !0,
        swh: "sw",
        ta: !0,
        te: !0,
        th: !0,
        tl: "fil",
        tr: !0,
        uk: !0,
        ur: !0,
        vi: !0,
        yi: !1,
        zh: "zh_cn",
        zh_cn: !0,
        zh_hk: !0,
        zh_tw: !0,
        zsm: "ms",
        zu: !0
    };
    google.a.b.U = {};
    google.a.b.J = "unknown";
    google.a.b.log = Y();
    google.a.b.error = Y();
    google.a.b.$ = !1;
    google.a.b.R = window;
    google.a.b.vd = {
        1: "1.0",
        "1.0": "current",
        "1.1": "upcoming",
        current: "44",
        upcoming: "45",
        41: "top",
        42: "top",
        43: "top",
        44: "top"
    };
    google.a.b.Va = {
        gstatic: {
            prefix: "/otherJS",
            debug: "{prefix}/debug/{version}/jsapi_debug_{package}_module.js",
            compiled: "{prefix}/{version}/js/jsapi_compiled_{package}_module.js",
            i18n: "{prefix}/{version}/i18n/jsapi_compiled_i18n_{package}_module__{language}.js",
            css_prefix: "{prefix}/{version}/css",
            css: Za,
            css_debug: Za,
            third_party: ab,
            third_party_gen: ab
        }
    };
    google.a.b.i = google.a.b.Va.gstatic;
    google.a.b.Ec = {
        "default": [],
        format: [],
        ui: ["format", "default"],
        ui_base: ["format", "default"],
        annotatedtimeline: [Q],
        annotationchart: [Q, "controls", K, "table"],
        areachart: [Q, I],
        bar: [Q, L, S],
        barchart: [Q, I],
        browserchart: [Q],
        calendar: [Q],
        charteditor: [Q, K, O, Wa, "gauge", "motionchart", "orgchart", "table"],
        charteditor_base: [R, K, O, Wa, "gauge", "motionchart", "orgchart", "table_base"],
        columnchart: [Q, I],
        controls: [Q],
        controls_base: [R],
        corechart: [Q],
        gantt: [Q, L],
        gauge: [Q],
        geochart: [Q],
        geomap: [Q],
        geomap_base: [R],
        helloworld: [Q],
        imageareachart: [Q, O],
        imagebarchart: [Q, O],
        imagelinechart: [Q, O],
        imagechart: [Q],
        imagepiechart: [Q, O],
        imagesparkline: [Q, O],
        intensitymap: [Q],
        line: [Q, L, S],
        linechart: [Q, I],
        map: [Q],
        motionchart: [Q],
        orgchart: [Q],
        overtimecharts: [Q, K],
        piechart: [Q, I],
        sankey: ["d3", "d3.sankey", Q],
        scatter: [Q, L, S],
        scatterchart: [Q, I],
        table: [Q],
        table_base: [R],
        timeline: [Q, L],
        treemap: [Q],
        wordtree: [Q]
    };
    google.a.b.pd = {d3: "d3/d3.js", "d3.sankey": "d3/d3.sankey.js", webfontloader: "webfontloader/webfont.js"};
    google.a.b.Hb = {dygraph: "dygraphs/dygraph-tickers-combined.js"};
    google.a.b.Bc = {
        annotatedtimeline: "/annotatedtimeline/annotatedtimeline.css",
        annotationchart: "annotationchart/annotationchart.css",
        charteditor: "charteditor/charteditor.css",
        charteditor_base: "charteditor/charteditor_base.css",
        controls: "controls/controls.css",
        imagesparkline: "imagesparkline/imagesparkline.css",
        intensitymap: "intensitymap/intensitymap.css",
        orgchart: "orgchart/orgchart.css",
        table: "table/table.css",
        table_base: "table/table_base.css",
        ui: ["util/util.css", "core/tooltip.css"],
        ui_base: "util/util_base.css"
    };
    google.a.b.va = function (a, c) {
        c = c || {};
        for (var d = [], e = 0; e < a.length; e++) {
            var f = a[e];
            if (!c[f]) {
                c[f] = !0;
                var g = google.a.b.Ec[f] || [];
                0 < g.length && (d = d.concat(google.a.b.va(g, c)));
                d.push(f)
            }
        }
        return d
    };
    google.a.b.Jc = function (a) {
        for (var c = {}, d = [], e = 0; e < a.length; e++) {
            var f = google.a.b.Bc[a[e]];
            Z.isArray(f) || (f = [f]);
            for (var g = 0; g < f.length; g++) {
                var h = f[g];
                h && !c[h] && (c[h] = !0, d.push(h))
            }
        }
        return d
    };
    google.a.b.jd = function (a, c) {
        if (c)if ("undefined" === typeof a.onload) {
            var d = !1;
            a.onreadystatechange = function () {
                d || (a.readyState && a.readyState !== J ? google.a.b.R.setTimeout(a.onreadystatechange, 0) : (d = !0, delete a.onreadystatechange, google.a.b.R.setTimeout(c, 0)))
            }
        } else a.onload = c
    };
    google.a.b.Cb = function (a, c, d) {
        google.a.b.log("loadScript: " + a);
        var e = c.createElement("script");
        e.type = Ya;
        e.language = "javascript";
        e.async = !1;
        e.defer = !1;
        c = c.body || c.head || c.getElementsByTagName("HEAD").item(0) || c.documentElement;
        c.insertBefore(e, c.lastChild);
        d && google.a.b.jd(e, d);
        e.src = a;
        google.a.b.log("end loadScript: " + a)
    };
    google.a.b.Xc = function (a, c) {
        function d(c) {
            var e = google.a.b.ub, f = a[c++];
            if (f) {
                var g = f, h = google.a.b.pd[f];
                h ? (g = h, f === S && (e = window.document), f = google.a.b.i.third_party) : google.a.b.Hb[f] ? (g = google.a.b.Hb[f], f = google.a.b.i.third_party_gen) : f = google.a.b.$ ? m : U ? w : n;
                g = f.replace($a, l).replace(bb, pb).replace("{language}", U).replace("{package}", g);
                google.a.b.Cb(g, e);
                d(c)
            }
        }

        function e() {
            for (var d = [], e = 0; e < a.length; e++)d.push(nb[a[e]]);
            eval("0,(function(){" + d.join("") + "})")();
            google.a.b.R.setTimeout(c, 0)
        }

        a = google.a.b.va(a);
        for (var f = [], g = 0; g < a.length; g++) {
            var h = a[g];
            google.a.b.U[h] || f.push(h)
        }
        a = f;
        google.a.b.log("Load packages + dependencies - previous: " + a);
        var l = google.a.b.i.prefix, m = google.a.b.i.debug, n = google.a.b.i.compiled, w = google.a.b.i.i18n, pb = google.a.b.J, U = google.a.b.Oa;
        "en" === U && (U = null);
        var nb = {}, ob = a.length;
        google.a.b.cd = function (a, c) {
            google.a.b.log("callback after loading " + a);
            nb[a] = c;
            google.a.b.U[a] = !0;
            ob--;
            0 === ob && e()
        };
        d(0)
    };
    google.a.b.X = function (a) {
        function c() {
            g = !0;
            for (var a = e.length, c = 0; c < a; c++)e[c]()
        }

        function d() {
            h = !0;
            for (var a = f.length, c = 0; c < a; c++)f[c]()
        }

        var e = [], f = [], g = !1, h = !1;
        google.a.b.X.count || (google.a.b.X.count = 0);
        var l = "load-css-" + google.a.b.X.count++, m = {
            done: function (a) {
                e.push(a);
                g && a();
                return m
            }, Ca: function (a) {
                f.push(a);
                h && a();
                return m
            }
        }, n = document.createElement("link");
        n.setAttribute("id", l);
        n.setAttribute("rel", "stylesheet");
        n.setAttribute("type", "text/css");
        "undefined" !== typeof n.addEventListener ? (n.addEventListener("load",
            c, !1), n.addEventListener("error", d, !1)) : "undefined" !== typeof n.attachEvent && n.attachEvent("onload", function () {
            var a, e = document.styleSheets.length;
            try {
                for (; e--;)if (a = document.styleSheets[e], a.id === l) {
                    c();
                    return
                }
            } catch (f) {
            }
            g || d()
        });
        document.getElementsByTagName("head")[0].appendChild(n);
        n.setAttribute("href", a);
        return m
    };
    google.a.b.Tc = function (a, c) {
        google.a.b.log("loadCSSFile: " + a);
        google.a.b.X(a).done(c).Ca(function () {
            google.a.b.error("loading css failed: " + a)
        })
    };
    google.a.b.Uc = function (a, c) {
        a = google.a.b.va(a);
        var d = google.a.b.Jc(a);
        if (null === d || 0 === d.length)c(); else {
            google.a.b.log("Loading css files: " + d.join(", "));
            var e = google.a.b.i.prefix, f = google.a.b.i.css;
            google.a.b.$ && (f = google.a.b.i.css_debug || f);
            var g = google.a.b.J, h = function (a) {
                var m = d[a], n;
                n = a < d.length - 1 ? function () {
                    h(a + 1)
                } : c;
                google.a.b.U[m] ? (google.a.b.log("Already loaded " + m), google.a.b.R.setTimeout(n, 0)) : (google.a.b.U[m] = !0, m = f.replace("{css_prefix}", google.a.b.i.css_prefix).replace($a, e).replace(bb,
                    g).replace("{cssFile}", m), google.a.b.Tc(m, n))
            };
            h(0)
        }
    };
    google.a.b.Ne = function () {
        var a = google.a.b.D;
        if (!a) {
            a = google.a.b.D = document.createElement("iframe");
            google.a.b.D = a;
            a.name = N;
            (document.body || document.head || document).appendChild(a);
            a.style.display = "none";
            var c = google.a.b.ub = a.contentDocument ? a.contentDocument : a.contentWindow ? a.contentWindow.document : a.document;
            c.open();
            c.writeln("");
            c.close()
        }
        return a
    };
    google.a.b.Fb = function (a) {
        for (var c = a.replace(/-/g, "_").toLowerCase(); Z.h(c);)a = c, c = google.a.b.languages[c], c === a && (c = !1);
        c || (a.match(/_[^_]+$/) ? (a = a.replace(/_[^_]+$/, ""), a = google.a.b.Fb(a)) : a = "en");
        return a
    };
    google.a.b.fd = function (a, c) {
        c.log && (google.a.b.log = c.log);
        c.error && (google.a.b.error = c.error);
        var d = c.debug, e = c.language || "", e = google.a.b.Fb(e);
        a || (a = c.version || "unknown");
        (google.a.b.J && google.a.b.J !== a || google.a.b.Oa && google.a.b.Oa !== e || google.a.b.$ !== d) && google.a.b.D && google.a.b.D.parentNode && (google.a.b.D.parentNode.removeChild(google.a.b.D), google.a.b.D = null, google.a.b.U = {});
        google.a.b.J = a;
        google.a.b.Oa = e;
        google.a.b.$ = d
    };
    google.a.b.T = !1;
    google.a.b.oa = !1;
    google.a.b.loaded = !1;
    google.a.b.Da = [];
    google.a.b.load = function (a, c, d) {
        var e;
        e = a.match(/^(testing\/)?(.*)/);
        var f = e[1] || "";
        for (a = e[2]; ;) {
            e = google.a.b.vd[a];
            if (null == e || "top" === e)break;
            a = e
        }
        google.a.b.i = d || google.a.b.Va[a] || google.a.b.Va.gstatic;
        a = f + a;
        if (null == e)f = function () {
            Z.Ga(N)(a, c, d);
            Z.Ga(Xa)(g)
        }, google.a.b.T ? google.a.b.N(f) : (google.a.b.T = !0, google.a.b.Cb(google.a.b.i.prefix + "/" + a + "/loader.js", window.document, f)); else {
            if (google.a.b.T)throw Error("google.charts.load() cannot be called more than once with version 45 or earlier.");
            google.a.b.T = !0;
            if (google.a.b.oa)google.a.b.N(function () {
                google.a.b.load(a, c)
            }); else {
                google.a.b.oa = !0;
                google.a.b.loaded = !1;
                var g = function () {
                    google.a.b.oa = !1;
                    google.a.b.loaded = !0;
                    google.a.b.Pa()
                };
                google.a.b.fd(a, c);
                google.a.b.log("google.charts.load version " + a);
                window.google = window.google || {};
                google.visualization = google.visualization || {};
                google.visualization.ModulePath = google.a.b.i.prefix;
                google.visualization.CssPath = google.a.b.i.css_prefix.replace($a, google.a.b.i.prefix).replace(bb, google.a.b.J);
                google.a.b.D =
                    null;
                google.a.b.R = window;
                google.a.b.ub = document;
                var h = c.packages;
                h && 0 !== h.length || (h = ["default"]);
                google.a.b.N(c.callback);
                google.a.b.Uc(h, function () {
                    google.a.b.Xc(h, g)
                })
            }
        }
    };
    google.a.b.N = function (a) {
        a && google.a.b.Da.push(a);
        google.a.b.loaded && !google.a.b.oa && google.a.b.Pa()
    };
    google.a.b.kd = function (a) {
        if (window.addEventListener)window.addEventListener("load", a, !1); else if (window.attachEvent)window.attachEvent("onload", a); else {
            var c = window.onload;
            window.onload = function (d) {
                c && c(d);
                a()
            }
        }
    };
    google.a.b.Mb = document && document.readyState === J;
    google.a.b.kd(function () {
        google.a.b.Mb = !0;
        google.a.b.Pa()
    });
    google.a.b.Pa = function () {
        if (google.a.b.T && google.a.b.loaded && (google.a.b.Mb || document.readyState === J))for (; 0 < google.a.b.Da.length;)google.a.b.Da.shift()()
    };
    google.a.b.Sa = function (a, c) {
        google.a.b.cd(a, c)
    };
    if (Z.Ga(N))throw Error("Google Charts loader.js can only be loaded once.");
    google.a.load = function () {
        var a = 0;
        "visualization" === arguments[a] && a++;
        var c = "current";
        Z.h(arguments[a]) && (c = arguments[a], a++);
        var d = {};
        arguments.length > a && (d = arguments[a], a++);
        var e = void 0;
        arguments.length > a && (e = arguments[a]);
        google.a.b.load(c, d, e)
    };
    google.a.N = function (a) {
        google.a.b.N(a)
    };
    google.a.Sa = function (a, c) {
        google.a.b.Sa(a, c)
    };
    Z.Ba(N, google.a.load);
    Z.Ba(Xa, google.a.N);
    Z.Ba("google.charts.packageLoadedCallback", google.a.Sa);
})();