# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# TODO: Set this  with the path to your assignments rep.  Use ssh protocol and see lecture notes
# about how to setup ssh-agent for passwordless access
SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-Mostafa-elfallal.git;protocol=ssh;branch=main \
		   file://aesdchar_init \
           "

PV = "1.0+git${SRCPV}"
# TODO: set to reference a specific commit hash in your assignment repo
SRCREV = "2b45cccb4bdd865219a12970adc66a4bf9ca6442"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
RPROVIDES:${PN} += "kernel-module-aesdchar"
inherit update-rc.d
INITSCRIPT_NAME = "aesdchar_init"
INITSCRIPT_PARAMS = "start 90 5 2 . stop 20 0 1 6 ."
do_install:append () {
	install -d ${D}${INIT_D_DIR}
	install -m 0755 ${WORKDIR}/${INITSCRIPT_NAME} ${D}${INIT_D_DIR}/
}
FILES:${PN} += "${INIT_D_DIR}/${INITSCRIPT_NAME}"