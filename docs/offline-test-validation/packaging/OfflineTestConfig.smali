.class public final Lcom/quanhoang/greenfarm3/S800x480/OfflineTestConfig;
.super Ljava/lang/Object;
.source "OfflineTestConfig.kt"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/quanhoang/greenfarm3/S800x480/OfflineTestConfig$Companion;
    }
.end annotation

.annotation runtime Lkotlin/Metadata;
    d1 = {
        "\u0000\u000c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0008\u0004\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\u0008\u0002\u00a2\u0006\u0004\u0008\u0002\u0010\u0003\u00a8\u0006\u0005"
    }
    d2 = {
        "Lcom/quanhoang/greenfarm3/S800x480/OfflineTestConfig;",
        "",
        "<init>",
        "()V",
        "Companion",
        "Green Farm 3:app_debug"
    }
    k = 0x1
    mv = {
        0x2,
        0x4,
        0x0
    }
    xi = 0x30
.end annotation


# static fields
.field public static final Companion:Lcom/quanhoang/greenfarm3/S800x480/OfflineTestConfig$Companion;

.field public static final ENABLED:Z = true


# direct methods
.method static constructor <clinit>()V
    .registers 2

    new-instance v0, Lcom/quanhoang/greenfarm3/S800x480/OfflineTestConfig$Companion;

    const/4 v1, 0x0

    invoke-direct {v0, v1}, Lcom/quanhoang/greenfarm3/S800x480/OfflineTestConfig$Companion;-><init>(Lkotlin/jvm/internal/DefaultConstructorMarker;)V

    sput-object v0, Lcom/quanhoang/greenfarm3/S800x480/OfflineTestConfig;->Companion:Lcom/quanhoang/greenfarm3/S800x480/OfflineTestConfig$Companion;

    return-void
.end method

.method private constructor <init>()V
    .registers 1

    .line 2
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static final enabled()Z
    .registers 1
    .annotation runtime Lkotlin/jvm/JvmStatic;
    .end annotation

    sget-object v0, Lcom/quanhoang/greenfarm3/S800x480/OfflineTestConfig;->Companion:Lcom/quanhoang/greenfarm3/S800x480/OfflineTestConfig$Companion;

    invoke-virtual {v0}, Lcom/quanhoang/greenfarm3/S800x480/OfflineTestConfig$Companion;->enabled()Z

    move-result v0

    .line 5
    return v0
.end method
